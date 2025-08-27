package com.example.core.spec;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.reflect.Field;

public final class GenericSpecificationBuilder {

    private GenericSpecificationBuilder() {}

    public static <T> Specification<T> build(Map<String, String> filters, Class<T> entityClass) {
        if (filters == null || filters.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        Map<String, Class<?>> types = resolveFieldTypes(entityClass);

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                // Handle suffix-based operators
                if (key.endsWith("_gt") || key.endsWith("_lt") || key.endsWith("_between") || key.endsWith("_in")) {
                    String base = key.replaceFirst("_(gt|lt|between|in)$", "");
                    Class<?> type = types.get(base);
                    if (type == null) continue;

                    if (key.endsWith("_gt")) {
                        addGreaterThan(predicates, cb, root.get(base), type, value);
                    } else if (key.endsWith("_lt")) {
                        addLessThan(predicates, cb, root.get(base), type, value);
                    } else if (key.endsWith("_between")) {
                        addBetween(predicates, cb, root.get(base), type, value);
                    } else if (key.endsWith("_in")) {
                        addIn(predicates, cb, root.get(base), type, value);
                    }
                    continue;
                }

                // Default equality / like
                Class<?> type = types.get(key);
                if (type == null) continue;

                if (type == String.class) {
                    predicates.add(cb.like(cb.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
                } else if (type == Long.class || type == long.class) {
                    toLong(value).ifPresent(v -> predicates.add(cb.equal(root.get(key), v)));
                } else if (type == Integer.class || type == int.class) {
                    toInteger(value).ifPresent(v -> predicates.add(cb.equal(root.get(key), v)));
                } else if (type == Double.class || type == double.class) {
                    toDouble(value).ifPresent(v -> predicates.add(cb.equal(root.get(key), v)));
                } else if (type == Boolean.class || type == boolean.class) {
                    predicates.add(cb.equal(root.get(key), Boolean.parseBoolean(value)));
                } else if (type == Date.class) {
                    try {
                        Date parsed = parseDateFlexible(value);
                        // If date-only, filter whole day
                        if (value.length() <= 10) {
                            predicates.add(cb.between(root.get(key), startOfDay(parsed), endOfDay(parsed)));
                        } else {
                            predicates.add(cb.equal(root.get(key), parsed));
                        }
                    } catch (ParseException ignored) {}
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Map<String, Class<?>> resolveFieldTypes(Class<?> entityClass) {
        Map<String, Class<?>> map = new HashMap<>();
        for (Field f : entityClass.getDeclaredFields()) {
            map.put(f.getName(), f.getType());
        }
        // Also include superclass fields (e.g., BaseEntity.id)
        Class<?> sup = entityClass.getSuperclass();
        while (sup != null && sup != Object.class) {
            for (Field f : sup.getDeclaredFields()) {
                map.putIfAbsent(f.getName(), f.getType());
            }
            sup = sup.getSuperclass();
        }
        return map;
    }

    // --- Helper operators ---
    private static void addGreaterThan(List<Predicate> predicates, CriteriaBuilder cb, Path<?> path, Class<?> type, String raw) {
        if (type == Long.class || type == long.class) {
            toLong(raw).ifPresent(v -> predicates.add(cb.greaterThan(path.as(Long.class), v)));
        } else if (type == Integer.class || type == int.class) {
            toInteger(raw).ifPresent(v -> predicates.add(cb.greaterThan(path.as(Integer.class), v)));
        } else if (type == Double.class || type == double.class) {
            toDouble(raw).ifPresent(v -> predicates.add(cb.greaterThan(path.as(Double.class), v)));
        } else if (type == Date.class) {
            try {
                Date d = parseDateFlexible(raw);
                predicates.add(cb.greaterThan(path.as(Date.class), d));
            } catch (ParseException ignored) {}
        }
    }

    private static void addLessThan(List<Predicate> predicates, CriteriaBuilder cb, Path<?> path, Class<?> type, String raw) {
        if (type == Long.class || type == long.class) {
            toLong(raw).ifPresent(v -> predicates.add(cb.lessThan(path.as(Long.class), v)));
        } else if (type == Integer.class || type == int.class) {
            toInteger(raw).ifPresent(v -> predicates.add(cb.lessThan(path.as(Integer.class), v)));
        } else if (type == Double.class || type == double.class) {
            toDouble(raw).ifPresent(v -> predicates.add(cb.lessThan(path.as(Double.class), v)));
        } else if (type == Date.class) {
            try {
                Date d = parseDateFlexible(raw);
                predicates.add(cb.lessThan(path.as(Date.class), d));
            } catch (ParseException ignored) {}
        }
    }

    private static void addBetween(List<Predicate> predicates, CriteriaBuilder cb, Path<?> path, Class<?> type, String raw) {
        String[] parts = raw != null ? raw.split(",") : new String[0];
        if (parts.length != 2) return;
        String a = parts[0].trim();
        String b = parts[1].trim();
        if (type == Long.class || type == long.class) {
            Optional<Long> v1 = toLong(a); Optional<Long> v2 = toLong(b);
            if (v1.isPresent() && v2.isPresent()) predicates.add(cb.between(path.as(Long.class), v1.get(), v2.get()));
        } else if (type == Integer.class || type == int.class) {
            Optional<Integer> v1 = toInteger(a); Optional<Integer> v2 = toInteger(b);
            if (v1.isPresent() && v2.isPresent()) predicates.add(cb.between(path.as(Integer.class), v1.get(), v2.get()));
        } else if (type == Double.class || type == double.class) {
            Optional<Double> v1 = toDouble(a); Optional<Double> v2 = toDouble(b);
            if (v1.isPresent() && v2.isPresent()) predicates.add(cb.between(path.as(Double.class), v1.get(), v2.get()));
        } else if (type == Date.class) {
            try {
                Date d1 = parseDateFlexible(a);
                Date d2 = parseDateFlexible(b);
                predicates.add(cb.between(path.as(Date.class), d1, d2));
            } catch (ParseException ignored) {}
        }
    }

    private static void addIn(List<Predicate> predicates, CriteriaBuilder cb, Path<?> path, Class<?> type, String raw) {
        if (raw == null || raw.isEmpty()) return;
        String[] parts = raw.split(",");
        if (type == String.class) {
            List<String> values = new ArrayList<>();
            for (String p : parts) {
                String v = p.trim().toLowerCase();
                if (!v.isEmpty()) values.add(v);
            }
            if (!values.isEmpty()) {
                predicates.add(cb.lower(path.as(String.class)).in(values));
            }
        } else if (type == Long.class || type == long.class) {
            List<Long> vals = new ArrayList<>();
            for (String p : parts) toLong(p.trim()).ifPresent(vals::add);
            if (!vals.isEmpty()) predicates.add(path.as(Long.class).in(vals));
        } else if (type == Integer.class || type == int.class) {
            List<Integer> vals = new ArrayList<>();
            for (String p : parts) toInteger(p.trim()).ifPresent(vals::add);
            if (!vals.isEmpty()) predicates.add(path.as(Integer.class).in(vals));
        } else if (type == Double.class || type == double.class) {
            List<Double> vals = new ArrayList<>();
            for (String p : parts) toDouble(p.trim()).ifPresent(vals::add);
            if (!vals.isEmpty()) predicates.add(path.as(Double.class).in(vals));
        } else if (type == Date.class) {
            List<Date> vals = new ArrayList<>();
            for (String p : parts) {
                try { vals.add(parseDateFlexible(p.trim())); } catch (Exception ignored) {}
            }
            if (!vals.isEmpty()) predicates.add(path.as(Date.class).in(vals));
        }
    }

    private static Optional<Long> toLong(String s) {
        try { return Optional.of(Long.parseLong(s)); } catch (Exception e) { return Optional.empty(); }
    }
    private static Optional<Integer> toInteger(String s) {
        try { return Optional.of(Integer.parseInt(s)); } catch (Exception e) { return Optional.empty(); }
    }
    private static Optional<Double> toDouble(String s) {
        try { return Optional.of(Double.parseDouble(s)); } catch (Exception e) { return Optional.empty(); }
    }

    private static Date parseDateFlexible(String input) throws ParseException {
        String[] patterns = new String[] {
                "yyyy-MM-dd",
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                "yyyy-MM-dd'T'HH:mm:ssXXX",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss"
        };
        ParseException last = null;
        for (String p : patterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(p);
                sdf.setLenient(false);
                return sdf.parse(input);
            } catch (ParseException ex) {
                last = ex;
            }
        }
        throw last != null ? last : new ParseException("Unparseable date: " + input, 0);
    }

    private static Date startOfDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    private static Date endOfDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }
}
