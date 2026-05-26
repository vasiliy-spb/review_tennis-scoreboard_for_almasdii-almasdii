package TableTennis.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@UtilityClass
@Slf4j
public class ValidatorUtil {
    public static List<String> isNameCorrect(String firstName, String secondName){
        List<String> errors = new ArrayList<>();
        if(firstName == null || secondName == null){
            errors.add("name can not be null");
            return errors;
        }
        if(firstName.isEmpty() || secondName.isEmpty()){
            errors.add("Name can not be empty");
            return errors;
        }
        if(firstName.equalsIgnoreCase(secondName)){
            errors.add("first player name and second player name can not be the same");
        }
        if(!firstName.matches("^[a-zA-Zа-яА-ЯёЁ]+$")){
            errors.add("name must contain only letters");
        }
        if(!secondName.matches("^[A-ZА-Я].*")){
            errors.add("player name must start with capital letter");
        }
        if(firstName.length() > 20 || secondName.length() > 20){
            errors.add("player name max size is 20");
        }
        return errors;
    }
//    public String getErrors(){
//        Optional<String> reduce = errors.stream().reduce((a, b)
//                -> a + "\n" + b);
//        return reduce.get();
//    }
}
