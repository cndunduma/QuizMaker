package finalproject;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Random;

public class FinalProject {

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);

        //Two UsersInfo.txt files being read in for the hashmaps
        File file = new File("UsersInfo.txt");
        Scanner scan = new Scanner(file);
        File fileR = new File("UsersInfo.txt");
        Scanner in = new Scanner(fileR);

        HashMap<String, String> map = new HashMap<>(); //Creation of username & password hashmap
        //Map collection of key value pairs that uses <String, String> due to the keys & values being strings

        while (scan.hasNext()) {//While loop that reads the UsersInfo.txt file line by line, and creates an index after each tab
            String line = scan.nextLine();
            String[] title = line.split("\t");
            map.put(title[2], title[3]); //Placing usernames (key) and passwords (value) into the hashmap with the put method
        }

        HashMap<String, String> role = new HashMap<>(); //Creation of users' roles hashmap (Student or Instructor)

        while (in.hasNext()) {//While loop that reads the UsersInfo.txt file line by line, and creates an index after each tab
            String line = in.nextLine();
            String[] col = line.split("\t");
            role.put(col[4], col[2]);//Placing roles (key) and usernames (value) into the hashmap
        }

        //Declaring ArrayStacks for quiz questions & answers
        ArrayStack st = new ArrayStack(125);
        ArrayStack as = new ArrayStack(125);

        //TestBank.txt & Answers.txt files being read in for the ArrayStacks and Instructor options
        File file2 = new File("TestBank.txt");
        File file3 = new File("Answers.txt");
        Scanner test = new Scanner(file2);
        Scanner ans = new Scanner(file3);

        //Strings r & s declared for if statements regarding the user's role
        String r = ("Instructor");
        String s = ("Student");

        int attempts = 3;
        while (attempts <= 3) {//While loop tracks the user's attempts at logging in & completing the quiz. If unsuccessful after 3 attempts the program is exited

            System.out.println("Please enter your first name");
            String firstName = input.next();
            System.out.println("Please enter your last name");
            String lastName = input.next();
            System.out.println("Please enter your username");
            String Username = input.next();
            System.out.println("Please enter your password");
            String password = input.next();
            attempts--; //Attempts variable being decremented each time the loop is executed

            if (map.containsKey(Username) && map.containsValue(password)) {//Checking hashmap for the entered user's credentials
                System.out.println("Successfully logged in");

                LocalDateTime dateTime = LocalDateTime.now(); //Obtaining the date & time for the quiz report file name
                DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("MM_dd_yyyy HH_mm_ss"); //Changing the format of the date & time, so that it can be used in the file name
                String newDateTime = dateTime.format(formatDateTime); //Formatted date & time
                Instant start = Instant.now(); //Starting the elapsed time timer

                if (role.containsKey(s) && !role.containsValue(Username)) {//Checking role hashmap for user's role
                    System.out.println("\n");
                    System.out.println("Quiz:");
                    FileWriter quizReport = new FileWriter(Username + "_COSC_236_Quiz_" + newDateTime + ".txt"); //Creating the quiz report txt file
                    PrintWriter fileOutNew = new PrintWriter(quizReport);

                    while (test.hasNext()) { //Pushing the questions & answers to two different stacks
                        st.push(test.nextLine());
                        as.push(ans.nextLine());
                    }

                    //Declaring variables for the number of incorrect answers & number of questions
                    int incorrect = 0;
                    int count = 0;

                    //For loop that obtains 10 random numbers to pull stack indexes of the corresponding questions & answers for the quiz
                    Random random = new Random();
                    String qs = "";
                    for (int i = 0; i < 10; i++) {
                        int qNum = random.nextInt(124);
                        qs += "\n" + st.get(qNum);
                        System.out.println("Question " + (i + 1) + ": " + st.get(qNum));
                        System.out.println("Your Answer: ");
                        String answers = input.next();

                        //Writing the questions & user's answers to quiz report file
//                        fileOutNew.println("Question " + (i + 1) + ": " + st.get(qNum));
//                        fileOutNew.println("Your Answer: " + answers);

                        if (answers.equalsIgnoreCase("true") || answers.equalsIgnoreCase("false")) {//Checking if the user's answers are correct
                            if (!answers.equalsIgnoreCase(as.get(qNum))) {
                                incorrect++;
                            }

                            count++;
                        } else {
                            System.out.println("Please enter a correct true or false statement");
                            System.exit(0);
                        }

//                        System.out.println("Correct Answer: " + as.get(qNum));
//                        fileOutNew.println("Correct Answer: " + as.get(qNum));

                        if (count == 10) {

                            Instant finish = Instant.now(); //Stops timer
                            long timeElapsed = Duration.between(start, finish).toMillis(); //Elapsed time passed since start of quiz

                            System.out.println("\n");
                            System.out.println("Quiz Report:");
                            System.out.println("First Name: " + firstName);
                            System.out.println("Last Name: " + lastName);
                            System.out.println("Score: " + (count - incorrect) + "/10"); //Subtracts the number of incorrect answers from count to obtain the number of correct answers
                            System.out.println("Time Elapsed: " + timeElapsed / 1000 + " seconds "); //Divides millisecond by 1000 to get seconds passed

                        }
                    }

                    Instant finish = Instant.now(); //Stops timer
                    long timeElapsed = Duration.between(start, finish).toMillis(); //Elapsed time passed since start of quiz

                    //Writes all print lines to quiz report file
                    fileOutNew.println(qs);
                    fileOutNew.println("Quiz Report:");
                    fileOutNew.println(firstName + " " + lastName);
                    fileOutNew.println("Score: " + (count - incorrect) + "/10");
                    fileOutNew.println((timeElapsed / 1000) + " seconds");
                    fileOutNew.close();

                    scan.close();
                    test.close();
                    ans.close();

                    FileWriter userStats = new FileWriter(Username + "_Stats.txt", true); //Creating the stat report txt file

                    BufferedWriter u = new BufferedWriter(userStats); //Adding score to user's stat file
                    u.write("\n" + (count - incorrect));
                    u.close();

                    System.out.println("Please enter another username and password or done as the username to exit"); //Exits program depending on user's input
                    String exit = input.next();
                    if (exit.equals("done")) {
                        System.exit(0);
                    } else if (exit.equals(Username)) {
                        System.out.println("Please enter password");
                        String pass2 = input.next();
                        System.exit(0);
                    }
                    return;
                } else if (role.containsKey(r) && role.containsValue(Username)); //Displays the listed prompts if the user is an instructor
                System.out.println("\n");
                System.out.println("Options:");
                System.out.println("1. Register a new student"); //add user info to UsersInfo.txt file
                System.out.println("2. Display stats"); //min, max, average of user's attempted scores
                System.out.println("3. Add new questions"); //adding new question to TestBank.txt & answer to Answers.txt

                System.out.println("Please enter an option:");
                String option = input.next();

                if (option.equals("1")) {//Takes in new student information from instructor & adds it to the UsersInfo.txt file
                    System.out.println("Please enter a first name");
                    String fName = input.next();
                    System.out.println("Please enter a last name");
                    String lName = input.next();
                    System.out.println("Please enter a username");
                    String uName = input.next();
                    System.out.println("Please enter a password");
                    String passWord = input.next();
                    System.out.println("Please enter a role (Student or Instructor)");
                    String newRole = input.next();

                    //BufferedWriter appends text to a file, and the write method writes specific Strings to the file
                    BufferedWriter a = new BufferedWriter(new FileWriter(file, true)); //Opens the UsersInfo.txt file & adds new student information
                    a.write("\n");
                    a.write(fName);
                    a.write("\t");
                    a.write(lName);
                    a.write("\t");
                    a.write(uName);
                    a.write("\t");
                    a.write(passWord);
                    a.write("\t");
                    a.write(newRole);

                    a.close();
                }

                if (option.equals("2")) {
                    System.out.println("Please enter student's username");
                    String uNameIn = input.next();
                    File fileUserStats = new File(uNameIn + "_Stats.txt");
                    //Opening student stat file to calculate the average score
                    Scanner fileIn = new Scanner(fileUserStats);
                    System.out.println("Displaying Stats for: " + uNameIn);
                    int max = Integer.MIN_VALUE; //Declares the max value as the min value
                    int min = Integer.MAX_VALUE; //Declares the min value as the max value
                    double sum = 0.0;
                    double c = 0.0;
                    double average = 0.00;

                    while (fileIn.hasNextInt()) {//Calculating the average score from all scores recorded
                        sum += fileIn.nextInt(); //Adding each line
                        c++; //Number of scores
                        average = (sum / c);
                    }
                    fileIn.close();

                    //Opening student stat file again to obtain min & max scores made
                    Scanner fileIn2 = new Scanner(fileUserStats);
                    while (fileIn2.hasNext()) {
                        int score = fileIn2.nextInt();
                        if (score > max) {//Determine the max score from the stat file by comparing each score
                            max = score;
                        }

                        if (score < min) {//Determine the min score from the stat file by comparing each score
                            min = score;
                        }

                    }

                    System.out.println("The average score is " + average);
                    System.out.println("The minimum score is " + min);
                    System.out.println("The maximum score is " + max);
                    fileIn2.close();
                }

                if (option.equals("3")) {//Adds new question & answer to TestBank.txt & Answers.txt
                    System.out.println("Please enter the new question");
                    String newQuestion = input.next();//Input for new question
                    newQuestion += input.nextLine();//Grabs whole string of words
                    System.out.println("Please enter the question's answer (true or false)");
                    String newAnswer = input.next();//Input for new answer

                    //Opens TestBank.txt file & writes user inputted question to it
                    BufferedWriter output = new BufferedWriter(new FileWriter(file2, true));
                    output.write("\n");
                    output.write(newQuestion);
                    output.close();

                    //Opens Answers.txt file & writes user inputted answer to it
                    BufferedWriter output2 = new BufferedWriter(new FileWriter(file3, true));
                    output2.write("\n");
                    output2.write(newAnswer);
                    output2.close();
                }
                return;
            } //When user login attempts reach 0 the program will exit
            else {
                System.out.println("Incorrect username or password " + attempts + " attempts remaining");
            }
            if (attempts == 0) {//When attempts are 0, the program will terminate
                System.exit(0); //Termination
            }

        }
    }
}
