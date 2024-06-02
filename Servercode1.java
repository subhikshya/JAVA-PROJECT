package pp; 
import java.io.*; 
import java.net.*; 
public class Servercode1 { 
public static void main(String[] args) throws IOException { 
ServerSocket serverSocket = new ServerSocket(12345); 
System.out.println("Server started"); 
while (true) { 
Socket clientSocket = serverSocket.accept(); 
System.out.println("Client connected: " + clientSocket); 
new ServerThread(clientSocket).start(); 
} 
} 
} 
class ServerThread extends Thread { 
private Socket clientSocket; 
public ServerThread(Socket clientSocket) { 
this.clientSocket = clientSocket; 
} 
public void run() { 
try ( 
PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
BufferedReader in = new BufferedReader(new 
InputStreamReader(clientSocket.getInputStream())); 
) { 
String inputLine; 
while ((inputLine = in.readLine()) != null) { 
// Handle input from client and calculate result 
String result = calculateResult(inputLine.trim()); 
// Send result back to client 
out.println("Result: " + result); 
} 
} 
catch (IOException e) { 
System.err.println("Error handling client request: " + e.getMessage()); 
} 
} 
 
 
private String calculateResult(String expression) { 
try { 
String[] tokens = expression.split(" "); 
if (tokens.length != 3) { 
return "Invalid input format"; 
} 
double operand1 = Double.parseDouble(tokens[0]); 
double operand2 = Double.parseDouble(tokens[2]); 
String operator = tokens[1]; 
double result; 
switch (operator) { 
case "+": 
result = operand1 + operand2; 
break; 
case "-": 
result = operand1 - operand2; 
break; 
case "*": 
result= operand1 * operand2; 
break; 
case "/": 
if (operand2 == 0) { 
return "Division by zero is not allowed"; 
} 
result = operand1 / operand2; 
break; 
case "%": 
result = operand1 % operand2; 
break; 
default: 
return "Invalid operator"; 
} 
return String.valueOf(result); 
} 
catch (NumberFormatException e) { 
return "Invalid input format"; 
} 
} 
}