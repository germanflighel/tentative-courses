package domain;

/**
 * This design could be replaced with an abstract class and subclassing, but it would be more difficult to iterate over them.
 * Having objects as first class citizens would solve this easily
 */

public enum Modality {
    INDIVIDUAL() {
        @Override
        public Boolean acceptsStudentQuantity(int size) {
            return size == 1;
        }

        @Override
        public Boolean acceptsMoreStudents(int studentQuantity) {
            return false;
        }
    },
    GROUP() {
        @Override
        public Boolean acceptsStudentQuantity(int size) {
            return 0 < size && size <= MAX;
        }

        int MAX = 6;
        @Override
        public Boolean acceptsMoreStudents(int studentQuantity) {
            return studentQuantity < MAX;
        }
    };

    public abstract Boolean acceptsMoreStudents(int studentQuantity);

    public abstract Boolean acceptsStudentQuantity(int size);
}
