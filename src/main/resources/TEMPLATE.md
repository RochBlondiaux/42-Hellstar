<h1 align="center">
    %project_name%
</h1>

<p align="center">
	<b><i>%brief_description%</i></b><br>
    %description%
</p>

<p align="center">
	<img alt="GitHub code size in bytes" src="https://img.shields.io/github/languages/code-size/rochblondiaux/%project_name%?color=blueviolet" />
	<img alt="Number of lines of code" src="https://img.shields.io/tokei/lines/github/rochblondiaux/%project_name%?color=blueviolet" />
	<img alt="Code language count" src="https://img.shields.io/github/languages/count/rochblondiaux/%project_name%?color=blue" />
	<img alt="GitHub top language" src="https://img.shields.io/github/languages/top/rochblondiaux/%project_name%?color=blue" />
	<img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/rochblondiaux/%project_name%?color=brightgreen" />
</p>

<h3 align="center">
	<a href="#%EF%B8%8F-about">About</a>
	<span> · </span>
	<a href="#-index">Index</a>
	<span> · </span>
	<a href="#%EF%B8%8F-usage">Usage</a>
	<span> · </span>
	<a href="#-testing">Testing</a>
</h3>

---

## 🗣️ About

> _%aim%

## 📑 Index

`@root`

%index_header%
* [**📁 includes:**](includes/) contains all prototypes, structures and libraries includes.
* [**📁 mlx:**](mlx/) contains the minilibx sources.
* [**📁 srcs:**](srcs/) contains all project sources files.
    * [**📁 maths:**](srcs/maths/) contains all maths related files.
    * [**📁 maths:**](srcs/maths/) contains all maths related files.
    * [**📁 vectors:**](srcs/graphics/) contains all vectors (2D/3D) related files.
    * [**📁 utils:**](srcs/utils/) contains all utilities files.

%index%

`@/srcs/glib.c`
* `glib_init` - Initialize window and image with minilibx.
* `glib_start` - Initialize & start rendering.
* `glib_stop` - Destroy image and window.
* `register_loop_hook` - mlx_loop_hook function wrapped.
* `rregister_key_hook` - mlx_key_hook function wrapped.

## 🛠️ Usage

### Requirements

The function is written in C language and thus needs the **`gcc` compiler** and some standard **C libraries** to run.

### Instructions

_Coming soon_

## 📋 Testing

_Coming soon_