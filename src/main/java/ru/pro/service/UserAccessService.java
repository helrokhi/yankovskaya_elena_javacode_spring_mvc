package ru.pro.service;

public interface UserAccessService {
    void lockAccount(String login);

    void unlockAccount(String login);
}
