# GradeBar
The library is a custom view about "Personal center consumption points" in our old version app.
 
 - Verion 1.0  build by zyl & xw in 2014-12-11;

 - Opensource  in 2015-11-4 (in github);

#Screen
<img src="http://i.imgur.com/IhgOl1m.jpg" width="35%">




#Usage
In first version you will use GradeBar like this:

xml:
         
	<com.mlm.gradebar.GradeBar
        android:id="@+id/gradebar"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:background="#FFFFFF" />

java-code:

    GradeBar gradeBar = (GradeBar) findViewById(R.id.gradebar);
	gradeBar.setDividerList(pointsMapper.getMap(), true);
    gradeBar.setGrade(pointsInt);


#Import

Wait to upload to jcenter....

#License

Copyright 2015 meilishenqi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
