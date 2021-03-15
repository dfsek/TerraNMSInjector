# Terra NMS Injector

An evil addon for Terra

## What does it do

Terra's Bukkit implementation does not use internal `net.minecraft.server` (NMS)
code. This addon changes that.

## Why

Bukkit's world gen API is very limited. It makes it difficult to do things properly,
and frequently requires us to use roundabout ways to get seemingly simple things done.
This addon directly interfaces with the Minecraft server, bypassing the limited Bukkit
world gen API.

## Why isn't this in Terra?

This addon uses gross reflection hacks to do things like changing private fields, and
replacing chunk generators at runtime. These are far too "dirty" to go into the official release
of Terra. If you want the features this provides, it is available here as an addon.

## Should I use this in production?

Definitely not right now. There are currently features missing, and the addon currently does
not offer any noticeable advantages. Should you *ever* use this in production? Maybe, that is
your decision. Many plugins already do things like this, while I would not consider it *safe*,
it is not *unheard of*.