# PAZSpellExtension
spel-helpers
Some helpers to aid testing SpEL expression evaluation in PingDataGovernance and the Policy Administration GUI.

SensitiveWords
This is a class that contains a static utility method, redactContent(content: String) -> String. The method accepts string content, redacts words contained in the deny list, and returns the result. The deny list may be defined by placing a file, spel_helpers/sensitive-words.txt into the classpath (this would require another JAR file, most likely). Alternatively, a default deny list is used if such a file does not exist (see source for details).

Usage

Building the JAR
You may wish to build this project in order to generate the JAR file to be placed onto a consuming application classpath. This can be done using Maven.

mvn clean package


The resulting JAR is generated in target/spel-helpers-{VERSION}.jar.

Placing the JAR
The file can then be placed into the PAP classpath by dropping it into INSTANCE_ROOT/lib/extensions. Alternatively, it can be placed into the DG classpath by dropping it into the same location relative to that server root.

Modifying the configuration.yml
The PAP requires a property be added to the configuration.yml. This may either be done manually after running setup, or during setup by passing in the options.yml file. Changes to configuration.yml only take effect after restarting PAP.

core:
  AttributeProcessing.SpEL.AllowedClasses: "com.pingidentity.dg.spel_helpers.SensitiveWords"



Usage in policy
Generally I find that the easiest way to define static utility methods in SpEL is to define a Named Value Processor. This can be done by navigating to Trust Framework > Definitions > Processors, creating a new SpEL processor with Value type of "String" and a value of T(com.pingidentity.dg.spel_helpers.SensitiveWords).redactContent(#this). You can name this "SensitiveWords.redactContent()".
This gets you out of having to type the above more than once. Now when defining Policy Sets/Policies, you can add value processors of type "Named" and select what you just created.
