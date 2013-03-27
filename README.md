RedLaserDroid
=============

This is a test Mono for android project to check whether RedLaserSDK (www.redlaser.com) can be compiled using mono. Indeed this is possible as shown in the test application. The test code uses the RLSample application Java code that comes with the RedLaserSDK. It is also possible to accomplish the same task by creating appropriate Java bindings for the libredlaser.jar file (more complicated, I tried it but it needed a lot of fixes)

Thanks to the following useful links:

1. http://mono-for-android.1047100.n5.nabble.com/RedLaser-bindings-for-MonoDroid-td5111514.html

2. http://stackoverflow.com/questions/8686819/redlasersdk-java-from-monodroid

3. http://stackoverflow.com/questions/7483140/example-of-using-redlasersdk-in-monotouch-project

4. http://stackoverflow.com/questions/12005296/how-to-access-monodroid-resources-from-java-file-splash-screen
If you run into the problem "package R does not exist", you can simply hardcode the references to various resources in your java code by inspecting the generated R.java file (follow the link #3).

--
purple.coder +@+ yahoo.co.uk