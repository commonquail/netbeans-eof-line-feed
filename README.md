# NetBeans Plugin: EOF Line Feed

**Target:** NetBeans 8.x

This plugin ensures files are terminated by at least one line feed
character.

[Plugin page][plugin]

## Description

NetBeans allows users to delete the final line feed character added to
documents by default, and then does not restore it automatically. This
plugin does not attempt to prevent deletion of the final line feed
character, as that is a far less trivial solution to the problem with more
edge cases to account for -- PHPStorm 7 does this, and not completely
successfully. Instead, this plugin simply appends a line feed character to
documents that are not already terminated by a line feed.

The plugin is naive by design. If frequently editing files that require
special handling that conflicts with this plugin, you should probably avoid
using it.

[plugin]: http://plugins.netbeans.org/plugin/56323
