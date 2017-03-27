# Operation Santa

Operation Santa is a Christmas-themed platformer that I've made for the 2012 holidays (despite being released in February 2013).

It's strongly inspired by the masterpiece platformers from the 90's, such as **Commander Keen** and **Jill of the Jungle**. In 6 levels of snowball-packed action, you'll need to save Christmas from the Grinch and his thugs that kidnapped your elf helpers.

![Be advised, i have a stock of snowballs and i'm not afraid to use 'em!](http://rorkien.s3.amazonaws.com/opsanta/opsanta-action.png)

## Technical info

### Language

This game was made in Java, in the purest way possible (no external libraries, except for the sound libs), in pretty much an attempt to show the world that the old and busted AWT packages are still a go for small games.

Even if there's almost no documentation (save for the occasional commentary), it's still a well structured project, and should prove a nice way to learn object-oriented programming on game development.

### Graphics

All graphics are drawn on a `BufferedImage`'s underlying raster, which means all the rendering is done pixel by pixel, in a 640x480 pixel matrix. The image is then drawn on a `Canvas` instance.

It sounds slow (it's fast, but inefficient; it should show on bigger matrices), but it's hardware accelerated, double buffered (natively, Java works on it's own way if you flip the right switches) and runs at 60 frames per second.

### Level generation

The terrain is statically placed, but is dinamically decorated. You'll see that flowers, snow patches, flower pots and wall decorations change.

### Sound and music

It's been a while that i've made this game. Don't ask me why i used an external library for it.

The songs are some obscure 16-bit FM tracks that I've ripped and converted into OGG files. They didn't have any artist or track information (and I'm afraid i don't even have the original files anymore).
