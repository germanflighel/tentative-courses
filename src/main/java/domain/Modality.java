package domain;

/**
 * This design could be replaced with an abstract class and subclassing, but it would be more difficult to iterate over them.
 * Having objects as first class citizens would solve this easily
 */

public enum Modality {
    INDIVIDUAL() {
        @Override
        public Boolean acceptsMoreStudents(int studentQuantity) {
            return false;
        }
    },
    GROUP() {
        int MAX = 6;
        @Override
        public Boolean acceptsMoreStudents(int studentQuantity) {
            return studentQuantity < MAX;
        }
    };

    public abstract Boolean acceptsMoreStudents(int studentQuantity);
}
