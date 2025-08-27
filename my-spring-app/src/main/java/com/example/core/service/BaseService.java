package com.example.core.service;

import com.example.core.repository.BaseRepository;
import com.example.core.spec.GenericSpecificationBuilder;
import com.example.core.module.BaseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<T> {

    protected final BaseRepository<T, Long> repository;

    public BaseService(BaseRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public T save(T entity) {
        // Sauvegarde directe; la logique relationnelle vit dans les services spécifiques
        return repository.save(entity);
    }

    @Transactional
    public List<T> saveAll(List<T> entities) {
        return repository.saveAll(entities);
    }

    @Transactional
    public T update(Long id, T request) {
        // Vérifier l'existence puis sauvegarder l'objet fourni
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
        return repository.save(request);
    }

    @Transactional(readOnly = true)
    public Page<T> search(Class<T> entityClass, java.util.Map<String, String> filters, Pageable pageable) {
        Specification<T> spec = GenericSpecificationBuilder.build(filters, entityClass);
        return repository.findAll(spec, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        Optional<T> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) {
            return false; // ID inexistant
        }

        T entity = entityOpt.get();

        try {
            // --- Dissocier toutes les relations possibles ---
            // On utilise la réflexion pour dissocier OneToMany et ManyToMany
            Method[] methods = entity.getClass().getMethods();
            for (Method method : methods) {
                String name = method.getName();
                Class<?> returnType = method.getReturnType();
                Class<?>[] paramTypes = method.getParameterTypes();

                // Dissocier OneToMany ou ManyToMany (clear les collections)
                if ((name.startsWith("get") || name.startsWith("is")) &&
                        Collection.class.isAssignableFrom(returnType)) {
                    Collection<?> collection = (Collection<?>) method.invoke(entity);
                    if (collection != null) {
                        collection.clear();
                    }
                }

                // Dissocier potentiel OneToOne inverse (si le getter retourne une entité liée)
                if (name.startsWith("get") && BaseEntity.class.isAssignableFrom(returnType)) {
                    Object related = method.invoke(entity);
                    if (related != null) {
                        // Chercher un setter sur l'entité liée qui prend en paramètre le type de l'entité courante
                        Method[] relMethods = related.getClass().getMethods();
                        for (Method rm : relMethods) {
                            if (rm.getName().startsWith("set") && rm.getParameterCount() == 1) {
                                Class<?> p = rm.getParameterTypes()[0];
                                if (p.isAssignableFrom(entity.getClass())) {
                                    try { rm.invoke(related, new Object[]{null}); } catch (Exception ignored) {}
                                }
                            }
                        }
                    }
                }

                // Dissocier OneToOne ou ManyToOne (setter null) uniquement si le paramètre est une entité
                if (name.startsWith("set") && paramTypes.length == 1) {
                    Class<?> pType = paramTypes[0];
                    // Ne pas toucher aux collections ni aux types simples; uniquement aux entités
                    if (!Collection.class.isAssignableFrom(pType) && BaseEntity.class.isAssignableFrom(pType)) {
                        try {
                            method.invoke(entity, new Object[]{null});
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la dissociation des relations : " + e.getMessage(), e);
        }

        // Supprimer l’entité
        repository.delete(entity);
        return true;
    }

 
}