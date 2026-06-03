package TableTennis.validator;

import TableTennis.Exception.BadRequestException;
import TableTennis.Exception.MatchNotFoundException;
import TableTennis.Exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MatchValidator {
    public void validateNames(String first, String second) {
        List<String> errors = new ArrayList<>();
        if (first == null || second == null) {
            throw new BadRequestException("Names cannot be null");
        }
        if(first.isEmpty() || second.isEmpty()){
            errors.add("names cannot be empty");
        }

        if (!first.matches("^[a-zA-Zа-яА-ЯёЁ]+$")) {
            errors.add("First name invalid");
        }

        if (!second.matches("^[a-zA-Zа-яА-ЯёЁ]+$")) {
            errors.add("Second name invalid");
        }

        if (first.equalsIgnoreCase(second)) {
            errors.add("Names must be different");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join("\n", errors));
        }
    }
    public void validateFilterName(String name){
        List<String> errors = new ArrayList<>();
        if(name == null){
            return;
        }
        if(name.length() > 20){
            errors.add("name max size is 20");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(String.join("\n", errors));
        }

    }
    public void validatePage(int page) {
        if (page < 0) {
            throw new BadRequestException("Page cannot be negative");
        }

    }
}
