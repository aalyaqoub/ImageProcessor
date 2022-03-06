# Image Processing (OOD HW5)

#IMPORTANT
Complex image examples are stored in
https://drive.google.com/drive/folders/1MNFJCAP8PeiL_J-OF7Tuai0Z061RONjA?usp=sharing
as handin won't accept our files so we resorted to small 3x3 images. To run tests make sure to 
unzip files in res/example/. Additionally, if you wish to run the script enter IMGTESTSCRIPT.txt in 
the program args or run the program and enter```-file IMGTESTSCRIPT.txt```. To run the jar go open 
your terminal and navigate to the project directory and enter the following command in your 
terminal ```java -jar hw7.jar```.

##How to run the program
To run the script in IntelliJ enter ```-file IMGTESTSCRIPT.txt``` in the program args for ImageUtil 
and then run the program. To run the script cd to the project directory then run the following 
command ```java -jar hw7.jar -file IMGTESTSCRIPT.txt```

To run the program with text based interactivity in IntelliJ enter ```-text``` in the program args 
for ImageUtil and then run the program. To run the program with text interactivity cd to the 
project directory and run the following command ```java -jar hw7.jar -text```

To run the program with a GUI based interactivity in IntelliJ run ImageUtil with no program args. 
To run the program with GUI interactivity cd to the project directory and run the following
command ```java -jar hw7.jar```

##Output
The script will save its output in the following directory 
```res/scriptOutput/```. The tests will save their output in the following directory 
```res/testImages/output/```. If you'd like to save in another directory you can specify it when 
running the program.

## Command Examples
Bellow are the currently available commands which can be used in this image program.

### load 
loads file into working image directory.
```java
load res/example/OGImage.ppm og
load {path} {refrence name} // General form
```

### script
loads script and executes it.
```java
file IMGTESTSCRIPT.txt
file {path} // General form
```


### save
saves file to given location.
```java
save res/example/horizontal.ppm og-horizontal
save res/example/both-flip.ppm og-both-flip
save {destination path} {reference name} // General form
```


### brighten
This command will brighten the og image and refer to the output as og-brighter
```java
brighten 100 og og-brighter
brighten {increment amount} {refrence name} {new reference name} // General form
```
### darken
This command will darken the og image and refer to the output as og-darker
```java
darken 100 og og-darker
darken {increment amount} {refrence name} {new reference name} // General form
```

### red-component
This command will get the red-component greyscale of the og and refer to it as og-red
```java
red-component og og-red
red-component {refrence name} {new reference name} // General form
```
### green-component
This command will get the green-component greyscale of the og and refer to it as og-green
```java
green-component og og-green
green-component {refrence name} {new reference name} // General form
```
### blue-component
This command will get the blue-component greyscale of the og and refer to it as og-blue
```java
blue-component og og-blue
blue-component {refrence name} {new reference name} // General form
```

### intensity-component
This command will get the intensity-component greyscale of the og and refer to it as og-intensity
```java
intensity-component og og-intensity
intensity-component {refrence name} {new reference name} // General form
```

### luma-component
This command will get the luma-component greyscale of the og and refer to it as og-luma
```java
luma-component og og-luma
luma-component {refrence name} {new reference name} // General form
```

### values-component
This command will get the values-component greyscale of the og and refer to it as og-values
```java
values-component og og-values
values-component {refrence name} {new reference name} // General form
```

### vertical-flip
This command will get the vertical-flip of the og-blue and refer to it as og-vertical
```java
vertical-flip og-blue og-vertical
values-component {refrence name} {new reference name} // General form
```

### horizontal-flip
This command will get the horizontal-flip of the og-red and refer to it as og-horizontal
```java
horizontal-flip og-red og-horizontal
horizontal-flip {refrence name} {new reference name} // General form
```

### both-flip
This command will get both-flip of the og-green and refer to it as og-both-flip
```java
both-flip og-green og-both-flip
both-flip {refrence name} {new reference name} // General form
```


### ImageBlur
applies blur filter to image
```java
blur og og-blur
blur {refrence name} {new reference name} // General form
```

### sharpen
applies sharpening filter to image
```java
sharpen og og-sharpen
sharpen {refrence name} {new reference name} // General form
```


### greyscale-trans
applies greyscale color transformation to image
```java
greyscale-trans og og-greyscale
greyscale-trans {refrence name} {new reference name} // General form
```

### sepia-tone
applies sepia-tone filter to image.
```java
sepia-tone og og-sepia
sepia-tone {refrence name} {new reference name} // General form
```

### Mosaic
applies mosaic to image.
```java
mosaic 1000 og og-sharpen
mosaic {seed number} {refrence name} {new reference name} // General form
```

### Downsizing
applies downsizing to image.
```java
downsize 500 500 og og-downsize
downsize {new width} {new height} {refrence name} {new reference name} // General form
```

### Partial Process
applies partial process to image.
```java
partial mask sepia-tone og og-sepia
partial {refrence mask name} {command} {refrence name} {new reference name} // General form
```

## GUI Functionality
### File IO
- To load an image go to the File in the menu bar and select Open. Then select the desired image
file from the file selection.
- To save an image go to the File in the menu bar and select Save or click command S.
- To save as an image go to the File in the menu bar and select Save as or click command shift S.

### Brightness Change
- To increase the brightness enter the increment amount in the text box then click 
  change brightness.
- To decrease the brightness enter the decrement amount as a negative number in the text box
then click the change brightness button.

### Orientation Control
- Click flip vertically to flip the image vertically
- Click flip horizontally to flip the image horizontally

### Color Component Control
- Click the corresponding color component button.

### Filters Control
- Click the corresponding filter button.

### Transformation Control
- Click the corresponding transformation button.








## Image Source
Amjad Al-Julaih @amjad715 instagram
