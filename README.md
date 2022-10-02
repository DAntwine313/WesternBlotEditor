# WesternBlotEditor
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

