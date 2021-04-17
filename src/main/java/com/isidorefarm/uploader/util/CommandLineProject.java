package com.isidorefarm.uploader.util;

import java.util.HashMap;

public class CommandLineProject {

    protected static HashMap<String, String> commandLineArgs;
    protected static String type;

    public CommandLineProject() {
    }

    protected static void parseCommandLineArgs(String [] args) {
        commandLineArgs = new HashMap<String, String>();
        type = "";
        String name = "";
        String value = "";

        // process args
        for (int i = 0; i < args.length; i++) {
            name = args[i].trim();
            value = args[++i].trim();

            // remove dashes
            name = name.replaceAll("-", "").trim();

            System.out.println("name: " + name + ", value: " + value);

            // sanity check
            if (name.isEmpty())
                continue;

            // check for help name, set value
            if ( name.equals("help") )
                value = "help";

            // set pair
            commandLineArgs.put(name, value);

            // check for type, set convenience type var
            if ( name.equals("type") )
                type = commandLineArgs.get("type");

        }

    }

    public static boolean haveGoodArg(String name) {

        if ( commandLineArgs.containsKey(name) && !(commandLineArgs.get(name).isEmpty()) )
            return true;

        return false;
    }

    public static String getArg(String name) {
        return commandLineArgs.get(name);
    }

    protected static void typeNotSpecified() {
        System.out.println("\"type\" param is unknown.");
        System.out.println("exiting.");
    }

    public static class IntParam {

        private String name;
        private Integer value = null;
        private boolean exists = false;
        private boolean valid = false;

        public IntParam(String name) {
            this.name = name;

            // param exists
            if (commandLineArgs.containsKey(name))
                exists = true;
            else
                return;

            // attempt parse
            try {
                value = Integer.parseInt(commandLineArgs.get(name));
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println(name + " unable to parse integer.");
            }

        }

        public boolean isValid() { return valid; }
        public boolean exists() { return exists; }
        public Integer getValue() { return value; }
        private String getName() { return name; }

    }

}
