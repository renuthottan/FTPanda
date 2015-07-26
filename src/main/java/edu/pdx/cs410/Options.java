package edu.pdx.cs410;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kmacarenco on 7/13/15.
 */

public class Options {
    Map<String, commandInterface> options = new HashMap<String, commandInterface>();


    //Add new functionality here.
    //All commands definitions live in FTPMethod.java
    public Options() {
        String name;

        name = "help";
        options.put(name, new HelpCommand(name, "", "Print help for this application", this));

        name = "exit";
        options.put(name, new ExitCommand(name, "", "Quit the application"));

        name = "user";
        options.put(name, new UserCommand(name, "[uname] [passwd]", "Enter credentials to use for login"));

        name = "ftp";
        options.put(name, new FtpCommand (name, "[server_address] [port]", ""));

        name = "logoff";
        options.put(name, new LogoffCommand (name, "", "Close ftp connection"));

 		name = "rls";
        options.put(name, new ListRemoteCommand(name, "", "List files/directories in remote working directory"));

        name = "rmkdir";
        options.put(name, new MakeDirectoryRemoteCommand(name, "[dir name]", "Create directory in working directory on remote side"));

        name = "rpwd";
        options.put(name, new PWDRemoteCommand(name, "", "Print working directory on remote side"));

        name = "rcd";
        options.put(name, new CDRemoteCommand(name, "[path]", "Change working directory on remote side"));

        name = "lls";
        options.put(name, new ListLocalCommand (name, "", "List files/directories in local working directory"));

        name = "lcd";
        options.put(name, new CDLocalCommand (name, "[path relative to current local directory]", "Change working directory on local side"));

        name = "put";
        options.put(name, new PutCommand(name, "[filename] [destination path relative to current remote directory]", "Upload file from local working directory to remote path specified"));
    }



    public void action(Command command) {
        try {
            if (options.containsKey(command.name)) {
                commandInterface currentArg = options.get(command.name);
                currentArg.run(command);
            } else {
                System.out.println("No such command.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public commandInterface getCommandInfo(String name) {
        return options.get(name);
    }

    public Iterator iterator() {
        return options.entrySet().iterator();
    }

}

class NotImplemented extends Exception {
    public NotImplemented(String exc) {
        super("Argument "+exc + " Not yet Implemented");

    }
}

class InvalidCommand extends Exception {
    public InvalidCommand(String exc) {
        super("Invalid command: "+exc);
    }
}


