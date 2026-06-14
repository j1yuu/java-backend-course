package kkashin.dev.repository;

import kkashin.dev.model.Profile;
import kkashin.dev.util.TransactionHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository {
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public ProfileRepository(TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public Profile saveProfile(Profile profile) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(profile);
            return profile;
        });
    }

    public Profile findById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Profile.class, id);
        }
    }

    public void deleteProfile(Profile profile) {
        transactionHelper.executeInTransaction(session -> {
            session.remove(profile);
        });
    }
}
