package kkashin.dev.repository;

import kkashin.dev.model.User;
import kkashin.dev.util.TransactionHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public UserRepository(TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public User saveUser(User user) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(user);
            return user;
        });
    }

    public User findById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(User.class, id);
        }
    }

    public void deleteUser(User user) {
        transactionHelper.executeInTransaction(session -> {
            session.remove(user);
        });
    }
}
