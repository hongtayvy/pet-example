import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetFactory {

    private Scanner scanner = new Scanner(System.in);

    /**
     * This method runs the whole pet factory. This method allows you to select one of the following:
     * add, print, update, remove, and finish the program.
     *
     * @params
     * @returns
     */
    public static void runPetFactory(){
        Scanner stdin = new Scanner(System.in);
        String choice = "";
        List<Pet> petList = new ArrayList();

        System.out.print("Welcome to Build A Pet! ");

        boolean factoryFlag = false;
        while(!factoryFlag) {
            System.out.println("Which of the following you like to do: " +
                    "\n 1. Create a pet (create) " +
                    "\n 2. See the pet list (print) " +
                    "\n 3. Update a pet (update) " +
                    "\n 4. Remove a pet (remove) " +
                    "\n 5. Take the pet(s) home (home) " +
                    "\n\nEnter Choice: "
            );
            choice = stdin.nextLine();

            switch(choice){
                case "1", "create" -> petList.add(createPet(stdin));
                case "2", "print" -> printPetList(petList);
                case "3", "update" -> petList = updatePet(petList, stdin);
                case "4", "remove" -> petList = removePet(petList, stdin);
                case "5", "home" -> {
                    printPetList(petList);
                    System.out.println("I hope you enjoy the pets you've created!");
                    factoryFlag = true;
                }
            }
        }

        stdin.close();
    }

    /**
     * This method is used to create a pet. They will be asked for their base properties of:
     *  - Pet Type
     *  - Pet Name
     *  - Pet Age
     *  - Pet Color
     *
     * @params stdin
     * @returns Pet
     */
    private static Pet createPet(Scanner stdin){
        String petType = "", name = "", age = "", color = "";
        Pet pet;

        System.out.println("What kind of pet would you like to make " +
                "\n 1. Dog " +
                "\n 2. Bird " +
                "\n 3. Fish " +
                "\n\nEnter Choice:"
        );
        petType = validChoice(stdin.nextLine());

        if (petType.equalsIgnoreCase("None")) {
            System.out.println("We are unable to create whatever monstrosity you've asked us to. Now please leave!");
            pet = new Pet();
        } else {
            System.out.println("We can create that for you! We just need to ask you a few questions.");

            System.out.print("What would you like to name your " + petType +" ?: ");
            name = stdin.nextLine();

            System.out.print("How old do you want your " + petType +" to be ?: ");
            age = stdin.nextLine();

            while(!validNumber(age)){
                System.out.println("That was not a valid number. Please let me know how old you want your your " + petType +" to be ?: ");
                age = stdin.nextLine();
            }

            System.out.print("What color do you want your " + petType + " to be?: ");
            color = stdin.nextLine();

            pet = new Pet(petType, name, Integer.parseInt(age), color);
        }

        return pet;
    }

    /**
     * This method is used to print the list of current pet's you've create. This will print out:
     *  - Pet Type
     *  - Pet Name
     *  - Pet Age
     *  - Pet Color
     *
     * @param petList
     * @returns
     */
    private static void printPetList(List<Pet> petList){
        if(petList.size() > 0) {
            for (int i = 0; i < petList.size(); i++) {
                System.out.println("Pet Number " + (i+1) + " is:" + petList.get(i).toString());
            }
        } else {
            System.out.println("There are currently no pets created yet.");
        }
    }

    /**
     * This method is used to update a pet. We first print out the list for the user to see.
     * From here, we allow them to pick a pet from the list. This will be restricted if it is a valid number and
     * if the number is valid in the list. If not method will reprompt you until you've chosen correctly.
     *
     * The update logic works as:
     *  0. We obtain the position of the pet they want to remove.
     *  1. We create declare a new pet to hold the values of the original pet.
     *  2. We remove the original pet from the list.
     *  3. We go and update the pet until the user is satisfied
     *      - Pet Type
     *      - Pet Name
     *      - Pet Age
     *      - Pet Color
     *  4. We set the new pet to the declared pet from 2
     *  5. We add the pet to the list in the position that it was original in so that the user never knows what we actually did with the pet.
     *
     * @param petList
     * @param stdin
     * @returns List<Pet>
     */
    private static List<Pet> updatePet(List<Pet> petList, Scanner stdin){
        if(petList.size() <= 0){
            System.out.println("There are no pets to update yet. ");
        } else {
            printPetList(petList);
            String selectPet = "", updateChoice = "";
            System.out.println("Which one would you like to update?: ");
            selectPet = stdin.nextLine();

            while (!validNumber(selectPet) || (Integer.parseInt(selectPet) > petList.size())) {
                System.out.println("The number you've written is either not a number or larger than the size of the list. Please try again: ");
                selectPet = stdin.nextLine();
            }

            int petPosition = Integer.parseInt(selectPet) - 1;

            Pet updatePet = petList.get(petPosition);
            petList.remove(petPosition);

            boolean updateFlag = false;
            while (!updateFlag) {
                System.out.println("What would you like to update? " +
                        "\n 1. Name " +
                        "\n 2. Age " +
                        "\n 3. Color " +
                        "\n 4. Done: " +
                        "\n\nEnter Choice:"
                );
                updateChoice = stdin.nextLine();

                if (updateChoice.equalsIgnoreCase("4") || updateChoice.equalsIgnoreCase("done")){
                    updateFlag = true;
                } else {
                    updatePet = validUpdate(updateChoice, updatePet, stdin);
                }
            }

            petList.add(petPosition, updatePet);
        }

        return petList;
    }

    /**
     * We simply are removing a pet from the list here. We show them the list IF there are pets to show. From here,
     * we will prompt them which one they will remove. We remove the desired pet from the list, and return the new list.
     *
     * @param petList
     * @param stdin
     * @returns List<Pet>
     */
    private static List<Pet> removePet(List<Pet> petList, Scanner stdin){
        if(petList.size() <= 0){
            System.out.println("There are no pets to remove yet. ");
        } else {
            printPetList(petList);
            String selectPet = "", updateChoice = "";
            System.out.println("Which one would you like to remove?: ");
            selectPet = stdin.nextLine();

            while (!validNumber(selectPet) || (Integer.parseInt(selectPet) > petList.size())) {
                System.out.println("The number you've written is either not a number or larger than the size of the list. Please try again: ");
                selectPet = stdin.nextLine();
            }

            petList.remove(Integer.parseInt(selectPet) - 1);
        }

        return petList;
    }


    /**
     * This method is to make sure that we have a valid number string. Otherwise it will return false.
     * @param number
     * @return boolean
     */
    private static boolean validNumber(String number){
        String regex = "[0-9]+";

        Pattern p = Pattern.compile(regex);

        if (number == null) {
            return false;
        }

        Matcher m = p.matcher(number);
        return m.matches();
    }

    /**
     * This method makes sure that we have a valid choice. We will return the validated choice at the end.
     * If the choice does not match what we have, then we will return none.
     * @param choice
     * @return String
     */
    private static String validChoice(String choice){
        String validatedChoice = "";

        switch(choice.toLowerCase()){
            case "1", "dog" -> validatedChoice = "Dog";
            case "2", "bird" -> validatedChoice = "Bird";
            case "3", "fish" -> validatedChoice = "Fish";
            default -> validatedChoice = "None";
        }

        return validatedChoice;
    }

    /**
     * This method actually applies the update to the Pet Object itself.
     * @param update
     * @param pet
     * @param stdin
     * @return Pet
     */
    private static Pet validUpdate(String update, Pet pet, Scanner stdin){
        String validatedChoice = "";

        /**
         * Pet pet: (pet object)
         *  -type: dog
         *  -name: shawn
         *  -age: 28
         *  -color: white
         *
         *  Name Change Example:
         *      -you want to update shawn to peanut
         *      -currently your pet object's name is shawn
         *      -the pet object you wrote has a method to setName(String name){...}
         *      -you want to use the pet oject method setName to "update" your current
         *      pet object's name.
         *      -pet.setName("peanut");
         */

        switch(update.toLowerCase()){
            case "1", "name" ->{
                System.out.println("Your pet's name is currently: " + pet.getName());
                System.out.println("What name would you like them now: ");
                pet.setName(stdin.nextLine());
            }
            case "2", "age" -> {
                System.out.println("Your pet's age is currently: " + pet.getAge());
                System.out.println("What age would you like them now: ");
                String updatedAge = stdin.nextLine();
                while(!validNumber(updatedAge)){
                    System.out.println("That was not a valid number. Please let me know how old you want your pet to be ?:");
                    updatedAge = stdin.nextLine();
                }
                pet.setAge(Integer.parseInt(updatedAge));
            }
            case "3", "color" -> {
                System.out.println("Your pet's color is currently: " + pet.getColor());
                System.out.println("What color would you like them now: ");
                pet.setColor(stdin.nextLine());
            }
            default -> System.out.println("You did not select a valid option there will be no updates to any pet.");
        }

        return pet;
    }
}
