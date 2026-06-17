package TableTennis.utils;

import TableTennis.Exception.DataBaseException;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class TransactionManager {

    // В проекте не используются вложенные транзакции, поэтому реализовывать здесь функционал,
        // дающий возможность присоединиться к уже существующей (внешней) транзакции, избыточно.

    private final SessionFactory sessionFactory;

    public <T> T doInTransaction(Supplier<T> action){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();

        // Стоит удалять комментарии (вроде того, что указан в следующей строке) из кода перед тем, как выполнять коммит
        boolean isOwner = !transaction.isActive(); // мы открыли транзакцию?

        if (isOwner) {
            transaction.begin();
        }

        try {
            T result = action.get();

            // Тело блока if всегда нужно оборачивать в {}
            if (isOwner) transaction.commit();
            return result;
        }catch (HibernateException e){

            // Тело блока if всегда нужно оборачивать в {}
            // TODO: При исключении транзакция должна откатываться, а не коммититься
            if (isOwner) transaction.commit();

            // TransactionManager не должен заниматься оборачиванием исключений от БД в специализированные для проекта.
                // Это ответственность слоя DAO. Здесь достаточно просто откатывать транзакцию.
            throw new DataBaseException("Error with db",e);
        }
        catch (Exception e) {

            // Тело блока if всегда нужно оборачивать в {}
            // TODO: Перед откатом транзакции надо проверить, что она активна (isActive())
            // TODO: Откат транзакции тоже должен выполняться в блоке try-catch (см. файл "utils.md" в этом же пакете)
            if (isOwner) transaction.rollback();
            throw e;
        } finally {

            // Тело блока if всегда нужно оборачивать в {}
            // TODO: Закрывать сессию вручную не нужно.
                // Использование sessionFactory.getCurrentSession() означает, что управление жизненным циклом сессии делегировано фреймворку.
                // Ручное вмешательство в этот процесс с помощью session.close() является анти-паттерном.
            if (isOwner) session.close();
        }
    }
    public void doInTransaction(Runnable action){
        doInTransaction(()->{
            action.run();
            return null;
        });
    }
}
