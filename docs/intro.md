---
sidebar_position: 1
---

# 项目结构

目前, 引擎由 [Native 部分](https://github.com/PrimogemStudio/openminecraft-binding)和 [JVM 部分](https://github.com/PrimogemStudio/openminecraft)组成

## Native 部分

目前使用 xmake 构建系统, 包含如下项目
- freetype
- harfbuzz
- xxhash
- openal
- meshoptimizer
- yoga
- stb
- shaderc
- glfw

## Generate a new site

Generate a new Docusaurus site using the **classic template**.

The classic template will automatically be added to your project after you run the command:

```bash
npm init docusaurus@latest my-website classic
```

You can type this command into Command Prompt, Powershell, Terminal, or any other integrated terminal of your code editor.

The command also installs all necessary dependencies you need to run Docusaurus.

## Start your site

Run the development server:

```bash
cd my-website
npm run start
```

The `cd` command changes the directory you're working with. In order to work with your newly created Docusaurus site, you'll need to navigate the terminal there.

The `npm run start` command builds your website locally and serves it through a development server, ready for you to view at http://localhost:3000/.

Open `docs/intro.md` (this page) and edit some lines: the site **reloads automatically** and displays your changes.
