
<p align="center">
    <img src="https://avatars.githubusercontent.com/u/76408197?s=200&v=4" alt="lyra-logo" width="120px" height="120px"/>
</p>
    


<p align="center">
    <h1 align="center">🦔 SonicTab - Create a custom TabList faster than Sonic.</h1>
  <i>SonicTab is an API (Application Programming Interface) that will allow you to create customized TabList(s) easily and fastly. By offering you a simple to understand 2-Dimensional Grid system.
</p>




## 🏷️ Features

- 2D Grid system
- Multi-Line header and footer
- Fullscreen mode
- Cross platform

  
## 📂 Installation

Installation of SonicTab

```bash
git clone https://github.com/rootxls/SonicTab
```

Now you can open this folder with your IDE.
    
## 📐 Usage


**Template**

First, you'll have to create a new TabList template, this will contain all your TabList properties.
```java
private final TabListTemplate template = new TabListTemplate();
```

**Header / Footer**

Secondly, you can add Header & Footer, thoses features allows you to put text on top and bottom of the tablist.

```java
// HEADER
template.getHeader().addLines("Line 1", "Line 2");

// FOOTER
template.getFooter().addLines("Line 1", "Line 2");
```

**Body Customization**

Afterwards, you can add a Body. Originally this area contains the list of players. But with SonicTab you can modify it to add whatever you want. To get this part of your template, use:

```java
template.getBody();
```

Such as: <br>
<u>Columns: </u> Define the number of columns you want for your tablist:

```java
template.getBody().setColumns(3);
```

<u>RemoveBaseLines: </u> Removing base players:

```java
template.getBody().setRemoveBaseLines(true);
```
<u>Custom Lines: </u> Add or delete your own lines into the tablist body:

```java
// ADD
int ping = 0;
int x, z = 0;
template.getBody().addLine(new BodyLine("YOUR LINE", ping, x, z));

// REMOVE
template.getBody().removeLine(x, z); // THE LINE AT COORDINATES X & Z WILL BE DELETED AND REPLACED BY A BLANK LINE
```

With lines you can modify displayed head too:

```java
BodyLine line = new BodyLine("YOUR LINE", ping, x, z);

// TEXTURE & SIGNATURE CAN BE OPTAINED BY UPLOADING SKIN ON https://mineskin.org/
String texture = "";
String signature = "";

line.setSkin(texture, signature);
```


  
## 🚧 Roadmap

- Animation, sous forme de plusieurs TabListTemplate qui s'altèrnent sur une Runnable async

- Quand on supprime une ligne, remplacer celle-ci par une ligne vide

## ❓ FAQ

#### From which version can be used this API ?

From 1.8 to 1.17

  
## 🤝 Contributing

Contributions are always welcome!

See `contributing.md` for ways to get started.

Please adhere to this project's `code of conduct`.

  
## 📎 Related

Here are the projects for which this API has been developed:

- [LyraMC](https://github.com/LyraMC)

  
## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://bsnk.tk/)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/bastien-siniak/)

  
