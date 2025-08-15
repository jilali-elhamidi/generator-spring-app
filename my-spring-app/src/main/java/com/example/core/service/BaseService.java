package com.example.core.service;

import com.example.core.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
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
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
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

                // Dissocier OneToMany ou ManyToMany (clear les collections)
                if ((name.startsWith("get") || name.startsWith("is")) &&
                        List.class.isAssignableFrom(method.getReturnType())) {
                    List<?> list = (List<?>) method.invoke(entity);
                    if (list != null) {
                        list.clear();
                    }
                }

                // Dissocier OneToOne ou ManyToOne (setter null)
                if (name.startsWith("set")) {
                    // On pourrait ajouter des règles pour setter null pour les objets liés
                    // Ex : méthode de type setXyz(Object obj)
                    // Ici on laisse à l’utilisateur de surcharger si nécessaire
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