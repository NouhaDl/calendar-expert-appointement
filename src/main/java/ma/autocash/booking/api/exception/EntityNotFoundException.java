package ma.autocash.booking.api.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s not found with id: %d", entityName, id));
    }
}