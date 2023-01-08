# UMGCWesternBlotEditor

**MAINTAINED VERSION: https://github.com/jgoldmintz/WesternBE

This program integrates Image Magick with an user interface, for the purposes of better imaging for Western Blot gels.

For best results please create a folder for each image to work on as intermediates are created after each modification

Button/tools functions:
-
-brightness-contrast = brightness {x contrast}{%}
-
Adjust the brightness and/or contrast of the image.

Brightness and Contrast values apply changes to the input image. They are not absolute settings. A brightness or contrast
value of zero means no change. The range of values is -100 to +100 on each. Positive values increase the brightness or 
contrast and negative values decrease the brightness or contrast. To control only contrast, set the brightness=0. 
To control only brightness, set contrast=0 or just leave it off.

-edge radius
-
Detect edges within an image.

-Edge detection/band straightener
-
Detect edges within an image through canny edge detection algorithm and alters the detected item's orientation using a 
Hough-line algorithm. 

-Canny Edge Detection: {radius}+{lower-percent}+{upper-percent}

"The thresholds range from 0 to 100% (e.g. -canny 0x1+10%+30%) with {+lower-percent} < {+upper-percent}. If {+upper-percent}
is increased but {+lower-percent} remains the same, lesser edge components will be detected, but their lengths will be 
the same. If {+lower-percent} is increased but {+upper-percent} is the same, the same number of edge components will be 
detected but their lengths will be shorter. The default thresholds are shown. The radius{xsigma} controls a gaussian blur 
applied to the input image to reduce noise and smooth the edges".<sup>1</sup>


-Hough-line Transformation

"Use the Hough line detector with any binary edge extracted image to locate and draw any straight lines that it finds.
The process accumulates counts for every white pixel in the binary edge image for every possible orientation (for angles
from 0 to 179 in 1 deg increments) and distance from the center of the image to the corners (in 1 px increments). 
It stores the counts in an accumulator matrix of angle vs distance. The size of the accumulator will be 180x(diagonal/2). 
Next it searches the accumulator for peaks in counts and converts the locations of the peaks to slope and intercept in 
the normal x,y input image space. The algorithm uses slope/intercepts to find the endpoints clipped to the bounds of the 
image. The lines are drawn from the given endpoints. The counts are a measure of the length of the lines."<sup>2</sup>

<sup>1, 2</sup>The ImageMagick Development Team. (2021). ImageMagick. Retrieved from https://imagemagick.org


-get last image
-
Feature may only be used to undo changes once until another modification has been made to allow for some forgiveness when manipulating image.

-invert (negate)
-
Replace each pixel with its complementary color.

The red, green, and blue intensities of an image are negated. White becomes black, yellow becomes blue, etc.

-monochrome
-
Transform the image to black and white.

-reset
-
Get original image and remove all history.

-resize = width x length in pixels
-
Resize an image. For best results resize to smaller.

-show history
-
Feature in tool bar to show the history of image modifications.

When exporting history save results as '.txt' file.

-sigmoidal-contrast = contrast x mid-point
-
Increase the contrast without saturating highlights or shadows.

Increase the contrast of the image using a sigmoidal transfer function without saturating highlights or shadows. Contrast indicates how much to increase the contrast. For example, 0 is none, 3 is typical and 20 is a lot.

The mid-point indicates where the maximum change 'slope' in contrast should fall in the resultant image (0 is white; 50% is middle-gray; 100% is black).

Using a very high contrast will produce a sort of 'smoothed thresholding' of the image. Not as sharp (with high aliasing effects) of a true threshold, but with tapered gray-levels around the threshold mid-point.

