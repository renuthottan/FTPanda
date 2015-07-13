package edu.pdx.cs410;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by konstantin on 7/11/15.
 **/

public class FTPMethod {
    public Integer numberOfArguments;
    public String description;
    public String name;

    public FTPMethod(String nm, Integer numberOfArgs, String desc) {
        numberOfArguments = numberOfArgs;
        description = desc;
        name = nm;
    }
    public void run(String [] commands) {
        System.out.println("Should not call it");
    }

    public String HelpString() {
        return name+" "+description;
    }
}

class HelpCommand extends FTPMethod implements  commandInterface {
    Options optionsHost;
    public HelpCommand(String name, Integer numberOfArgs, String description, Options optionsHolder) {
        super(name, numberOfArgs, description);
        optionsHost = optionsHolder;
    }

    public void run(Command commands) {
        if (commands.length() == 1) {
            Iterator it = optionsHost.iterator();
            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println( ((commandInterface) pair.getValue()).HelpString());
            }
        } else {
            Iterator it = commands.iterator();
            while(it.hasNext()) {
                String name = (String) it.next();
                commandInterface commandInfo =  optionsHost.getCommandInfo(name);
                if (commandInfo == null) {
                    System.out.println("Unknown command "+name);
                } else {
                    System.out.println(commandInfo.HelpString());
                }
            }
            for (int i= 0; i< commands.length() ; i++ ) {
            }
        }

    }
}

class ExitCommand extends FTPMethod implements commandInterface {
    public ExitCommand(String name, Integer numberOfArgs, String description) {
        super(name, numberOfArgs, description);
    }

    public void run(Command commands) {
        System.out.println("Goodbye");
        System.exit(0);
    }
}

class UserCommand extends FTPMethod implements commandInterface {
    public UserCommand(String name, Integer numberOfArgs, String description) {
        super(name, numberOfArgs, description);
    }
    public void printUser(Command commands) {
        String user = commands.ftpConnection.cInfo.user;
        if (user.length() == 0) {
            System.out.println("No user set yet");
        } else {
            System.out.println(user);
        }
    }
    public void run(Command commands) throws InvalidCommand {
        String [] args = commands.arguments; //user [username] [password]
        if (args.length == 1)
        {
            printUser(commands);
            return;
        }

        if (args.length < 3) {
            throw new InvalidCommand ("User [username] [password]");
        }
        commands.ftpConnection.cInfo.user = args[1];
        commands.ftpConnection.cInfo.password = args[2];


    }
}

class FtpCommand extends FTPMethod implements commandInterface {
    public FtpCommand(String name, Integer numberOfArgs, String description) {
        super(name, numberOfArgs, description);
    }

    public void run(Command commands) throws InvalidCommand {
        String [] args = commands.arguments; //user [username] [password]
        if (args.length != 3) {
            throw new InvalidCommand ("ftp [ftp_server_address] [port]");
        }
        commands.ftpConnection.cInfo.server = args[1];
        commands.ftpConnection.cInfo.port = new Integer(args[2]);
        commands.ftpConnection.connect();
    }
}

class LogoffCommand extends FTPMethod implements commandInterface {
    public LogoffCommand(String name, Integer numberOfArgs, String description) {
        super(name, numberOfArgs, description);
    }

    public void run(Command commands) throws InvalidCommand {
        String [] args = commands.arguments; //user [username] [password]
        if (args.length != 1) {
            throw new InvalidCommand (name);
        }
        commands.ftpConnection.disconnect();
    }
}