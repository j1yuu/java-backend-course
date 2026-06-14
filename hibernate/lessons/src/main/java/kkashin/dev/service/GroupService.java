package kkashin.dev.service;

import kkashin.dev.TransactionHelper;
import kkashin.dev.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public GroupService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper =transactionHelper;
    }

    public Group saveGroup(String number, long gradYear) {
        return transactionHelper.executeInTransaction(session -> {
            Group group = new Group(number, gradYear);
            session.persist(group);

            return group;
        });
    }

    public List<Group> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("""
                       select g from Group g
                       left join fetch g.studentList s
                       left join fetch s.profile
                    """, Group.class)
                    .list();
        }
    }
}
