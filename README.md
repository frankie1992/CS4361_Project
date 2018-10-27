# CS4361_Project
Our Semester project is going to be our representation of Asteroids 

## Installation

### Requirements
* Java 8
* Gradle
* Homebrew

to install Homebrew

$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

to install Java 8

$ brew tap caskroom/versions
 
$ brew cask install java8

to install Gradle

$ brew install gradle

## Usage

Import into the intelliJ IDE

Select import from external model

select Gradle then hit next

Select the Auto Import box and hit finish

In the file path run desktop> src> com.mygdx.game.desktop> DesktopLauncher

its going to fail, go into the run menu and select "Edit Configurations"

Change the working Directory to 
projectDirectory/core/assets

now run again and it should work
