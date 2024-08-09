import java.io.*;
import java.util.*;
public class QuizGame { 
 private static Map<String, Quiz> quizzes = new 
HashMap<>();
 
 private static Map<String, Integer> userScores = new 
HashMap<>();
 
 private static final String SCORES_FILE_PATH = 
"user_scores.txt";
 
 public static void main(String[] args) { 
 
 loadScoresFromFile();
 
 Scanner sc = new Scanner(System.in);
 System.out.print("Enter your username: ");
 String enteredUsername = sc.nextLine();
 System.out.print("Enter your password: ");
 String enteredPassword = sc.nextLine();
 
 String pat = "\\d{4}-\\d{2}-\\d{3}-\\d{3}@vce\\.ac\\.in";
 
 if (enteredUsername.matches(pat) && enteredPassword.equals("vasavicc")) { 
 System.out.println("Authentication successful. Starting the quiz...\n");
 
 createQuizzes();
 
 Scanner scanner = new Scanner(System.in);
 while (true) { 
 System.out.println("Enter a command: (take, view, list, exit)");
 String command = scanner.nextLine();
 if (command.equals("take")) { 
 takeQuiz(scanner, enteredUsername);
 } else if (command.equals("view")) { 
 viewQuiz(scanner);
 } else if (command.equals("list")) { 
 listQuizzes();
 } else if (command.equals("exit")) { 
 saveScoresToFile();
 break;
 } else { 
 System.out.println("Invalid command.");
 } 
 } 
 } else { 
 System.out.println("Authentication failed.");
 } 
 sc.close();
 } 
 
 private static void createQuizzes() { 
 
 Quiz quiz1 = new Quiz("Java1");
 List<String> choices1 = Arrays.asList("char", "String", "int", "single");
 Question question1 = new Question("Which data type is used to store a single character in Java?", choices1, 0);
 List<String> choices2 = Arrays.asList("#","/","//","/");
 Question question2 = new Question("how do you insert comments in java code?",choices2, 2);
 
 List<String> choices3 = 
Arrays.asList("getSize()","getlength()","len()","length()");
 Question question3 = new Question("Which method can be used to find the length of a string?",choices3,2);
 List<String> choices4 = Arrays.asList("toUpperCase()","touppercase()","uppercase()","tuc");
 Question question4 = new Question("Which method can be used to return a string in upper case letters?",choices4,0);
 
 List<String> choices5 = Arrays.asList("MAVEN_Path","JavaPATH","Java","JAVA_HOME");
 Question question5 = new Question("Which environment variable is used to set the java path?",choices5,3);
 
 
 quiz1.addQuestion(question1);
 quiz1.addQuestion(question2);
 quiz1.addQuestion(question3);
 quiz1.addQuestion(question4);
 quiz1.addQuestion(question5);
 
 quizzes.put("Java1", quiz1);
 
 Quiz quiz2 = new Quiz("Java2");
 List<String> choices6 = Arrays.asList("Encapsulation", "Inheritance", "Polymorphism", "Abstraction");
 Question question6 = new Question("Which of the following is not one of the four main OOP concepts?", choices6, 0);
 
 List<String> choices7 = Arrays.asList("Compile￾time", "Runtime", "Logic");
 Question question7 = new Question("In Java, when is the static binding (compile-time) used?", choices7, 0);
 List<String> choices8 = Arrays.asList(" break"," continue"," for()","if()");
 Question question8 = new Question("Which of these are selection statements in Java?",choices8, 3);
 quiz2.addQuestion(question6);
 quiz2.addQuestion(question7);
 quiz2.addQuestion(question8);
 quizzes.put("Java2", quiz2);
 } 
 
 private static void takeQuiz(Scanner scanner, String username) { 
 System.out.println("Enter the name of the quiz:");
 String quizName = scanner.nextLine();
 Quiz quiz = quizzes.get(quizName);
 if (quiz == null) { 
 System.out.println("Quiz not found.");
 return;
 } 
 int score = 0;
 for (int i = 0; i < quiz.getNumQuestions(); i++) {
 Question question = quiz.getQuestion(i);
 System.out.println("Question " + (i + 1) + ": " + question.getQuestion());
 List<String> choices = question.getChoices();
 for (int j = 0; j < choices.size(); j++) {
 System.out.println((j + 1) + ": " + choices.get(j));
 } 
 System.out.println("Enter your answer:");
 int userAnswer = Integer.parseInt(scanner.nextLine()) - 1;
 if (userAnswer == question.getCorrectChoice()) { 
 System.out.println("Correct!");
 score++;
 } else { 
 System.out.println("Incorrect");
 } 
 } 
 System.out.println("Your score is " + score + " out of " + quiz.getNumQuestions() + ".");
 userScores.put(username, userScores.getOrDefault(username, 0) + score);
 
 } 
 
 private static void viewQuiz(Scanner scanner) { 
 System.out.println("Enter the name of the quiz:");
 String quizName = scanner.nextLine();
 Quiz quiz = quizzes.get(quizName);
 if (quiz == null) { 
 System.out.println("Quiz not found.");
 return;
 } 
 System.out.println("Quiz: " + quiz.getName());
 for (int i = 0; i < quiz.getNumQuestions(); i++) {
 Question question = quiz.getQuestion(i);
 System.out.println("Question " + (i + 1) + ": " + question.getQuestion());
 List<String> choices = question.getChoices();
 for (int j = 0; j < choices.size(); j++) {
 System.out.println((j + 1) + ": " + choices.get(j));
 } 
 System.out.println("Answer: " + (question.getCorrectChoice() + 1));
 } 
 } 
 
 private static void listQuizzes() { 
 System.out.println("Quizzes:");
 for (String quizName : quizzes.keySet()) { 
 System.out.println("- " + quizName);
 } 
 } 
 
 private static void saveScoresToFile() { 
 try (PrintWriter writer = new PrintWriter(new FileWriter(SCORES_FILE_PATH))) { 
 for (Map.Entry<String, Integer> entry : userScores.entrySet()) { writer.println(entry.getKey() + "," + entry.getValue());
 } 
 System.out.println("Scores saved to file.");
 } catch (IOException e) { 
 System.out.println("Error saving scores to file: " + 
e.getMessage());
 } 
 } 
 
 private static void loadScoresFromFile() { 
 try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE_PATH))) 
{ 
 String line;
 while ((line = reader.readLine()) != null) { 
 String[] parts = line.split(",");
 if (parts.length == 2) { 
 String username = parts[0];
 int score = Integer.parseInt(parts[1]);
 userScores.put(username, score);
 } 
 } 
 System.out.println("Scores loaded from file.");
 } catch (IOException | NumberFormatException e) { 
 System.out.println("Error loading scores from file: " + e.getMessage());
 } 
 } 
} 
class Quiz { 
 
 private String name;
 
 private List<Question> questions = new ArrayList<>();
 
 public Quiz(String name) { 
 this.name = name;
 } 
 
 public String getName() { 
 return name;
 } 
 
 public void addQuestion(Question question) { 
 questions.add(question);
} 
public Question getQuestion(int index) { 
 return questions.get(index);
} 
public int getNumQuestions() { 
 return questions.size();
} 
} 
class Question { 
 private String question;
private List<String> choices;
private int correctChoice;
public Question(String question, List<String> choices, int 
correctChoice) { 
 this.question = question;
 this.choices = choices;
 this.correctChoice = correctChoice;
} 
public String getQuestion() { 
 return question;
} 
public List<String> getChoices() { 
 return choices;
} 
public int getCorrectChoice() { 
 return correctChoice;
} 
}
