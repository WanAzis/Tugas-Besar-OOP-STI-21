# Set the compiler to use
JC = javac

# Set the compiler flags
JFLAGS = -d bin -cp src

# Define the list of source files to compile
SOURCES = $(wildcard src/**/*.java)

# Define the list of class files to generate
CLASSES = $(SOURCES:src/%.java=bin/%.class)

# Set the default target
default: classes

# Compile the Java source files into class files
classes: $(CLASSES)

# Define how to generate a class file from a Java source file
bin/%.class: src/%.java
	@mkdir -p $(@D)
	$(JC) $(JFLAGS) $<

# Clean up the generated class files
clean:
	$(RM) -r bin/*