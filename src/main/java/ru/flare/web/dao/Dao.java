package ru.flare.web.dao;

import java.util.List;

/**
 * User: Renato
 */
public interface Dao<T> {

	T getById(String id);

	void save(T... type);
}
