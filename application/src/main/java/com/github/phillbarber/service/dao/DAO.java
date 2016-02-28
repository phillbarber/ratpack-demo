package com.github.phillbarber.service.dao;

import rx.Observable;

public interface DAO {

    Observable<String> getValueFromDB();
}
