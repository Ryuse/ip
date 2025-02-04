# ✨ Hokmah ✨
> *Chokmah (Hebrew: חָכְמָה, romanized: ḥoḵmā, also transliterated as chokma, chokhmah or hokhma) is the Biblical Hebrew word rendered as "wisdom" in English Bible versions (LXX σοφία sophia, Vulgate sapientia) - [Wikipedia](https://en.wikipedia.org/wiki/Chokmah)

**Hokmah** is a chatbot that doesn't like you but will:
- Organize your tasks for you.
- Run on your computer.
That's it
 

---
## What can it do?
- [x] Add tasks (todos, events, deadlines)
- [x] Delete tasks
- [x] List tasks
- [X] Mark tasks as done
- [X] Find tasks by keyword
- [X] Find tasks by deadline (for deadlines and events)
- [ ] ~~Achieve Sentience~~

- [ ] ~~World Domination~~
---
## Setting up in Intellij
Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
2. Open the project into Intellij as follows:
   1. Click `Open`.
   2. Select the project directory, and click `OK`.
   3. If there are any further prompts, accept the defaults.
3. Configure the project to use **JDK 17** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
4. After that, locate the `src/main/java/hokmah.Hokmah.java` file, right-click it, and choose `Run hokmah.Hokmah.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
   I'm
   ,--.  ,--.         ,--.                         ,--.     
   |  '--'  |  ,---.  |  |,-.  ,--,--,--.  ,--,--. |  ,---.
   |  .--.  | | .-. | |     /  |        | ' ,-.  | |  .-.  |
   |  |  |  | ' '-' ' |  \  \  |  |  |  | \ '-'  | |  | |  |
   `--'  `--'  `---'  `--'`--' `--`--`--'  `--`--' `--' `--'
   
   What do you want?
   ```

---
## For Java Programmers that want to use Hokmah as a library
If you are a Java programmer, you can use it to practice Java too. Here's the main method:
```java
public class Main {
    public static void main(String[] args) {
        Hokmah hokmah = new Hokmah("data/tasks.txt");
        hokmah.run();
    }
}
```

---
### Warning
Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

Also, Hokmah might bite.
