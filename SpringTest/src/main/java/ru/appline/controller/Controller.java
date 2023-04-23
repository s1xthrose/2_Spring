package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);
    private static int petCount = 0; // Добавляем счетчик питомцев

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/json")
    public String createPet(@RequestBody Pet pet){
        petModel.add(pet, newId.getAndIncrement());
        petCount++; // Увеличиваем счетчик питомцев
        String message = "Поздравляем!";
        if(petCount > 1) { // Если создан не первый питомец, то не добавляем номер питомца
            message += " " + petCount + "-й питомец был только что создан.";
        } else { // Если создан первый питомец, то добавляем соответствующее сообщение
            message += " Первый питомец был только что создан.";
        }
        return "{\"message\": \"" + message + "\"}"; // Возвращаем json-файл с сообщением
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll(){
        return petModel.getAll();
    }

    /*
        {
            "id": 3
        }
     */
    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id){
        return petModel.getFromList(id.get("id"));
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/json")
    public String deletePet(@RequestBody Map<String, Integer> id){
        if(petModel.getFromList(id.get("id")) == null) { // Если питомец с таким id не найден
            return "{\"message\": \"Питомец не найден.\"}"; // Возвращаем сообщение об ошибке
        } else {
            petModel.removeFromList(id.get("id")); // Удаляем питомца из списка
            petCount--; // Уменьшаем счетчик питомцев
            String message = id.get("id") + "-й питомец был успешно удален.";
            if(petCount > 0) { // Если остались другие питомцы, то добавляем сообщение о их количестве
                message += " Осталось " + petCount + " питомцев.";
            } else { // Если питомцы закончились, то добавляем соответствующее сообщение
                message += " Питомцев больше нет.";
            }
            return "{\"message\": \"" + message + "\"}"; // Возвращаем json-файл с сообщением
        }
    }
    @PutMapping(value = "/putPet/{id}", consumes = "application/json", produces = "application/json")
    public String putPet(@RequestBody Pet pet, @PathVariable int id){
        if(petModel.getFromList(id) == null) { // Если пользователь с таким id не найден
            return "{\"message\": \"Пользователь не найден.\"}"; // Возвращаем сообщение об ошибке
        } else {
            Pet oldPet = petModel.getFromList(id); // получаем старые данные пользователя
            oldPet.setName(pet.getName()); // Редактируем имя
            oldPet.setType(pet.getType()); // Редактируем вид
            oldPet.setAge(pet.getAge()); // Редактируем возраст
            return "{\"message\": \"Питомец успешно отредактирован.\"}"; // Возвращаем json-файл с сообщением
        }
    }
}
