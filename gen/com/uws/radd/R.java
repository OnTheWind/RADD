/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package com.uws.radd;

public final class R {
    public static final class attr {
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarButtonStyle=0x7f010001;
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarStyle=0x7f010000;
    }
    public static final class color {
        public static final int black_overlay=0x7f040000;
    }
    public static final class drawable {
        public static final int action_mix=0x7f020000;
        public static final int action_queue=0x7f020001;
        public static final int button_shape=0x7f020002;
        public static final int glass=0x7f020003;
        public static final int ic_launcher=0x7f020004;
        public static final int radd_logo=0x7f020005;
    }
    public static final class id {
        public static final int Cancel=0x7f080012;
        public static final int LinearLayout1=0x7f080000;
        public static final int LinearLayout2=0x7f08000a;
        public static final int MainMenue=0x7f080023;
        public static final int QueueList=0x7f080022;
        public static final int Save=0x7f080013;
        public static final int TextView1=0x7f08000b;
        public static final int action_mix=0x7f080029;
        public static final int action_queue=0x7f080028;
        public static final int back=0x7f080024;
        public static final int bottle1=0x7f080001;
        public static final int bottle2=0x7f080005;
        public static final int bottle3=0x7f080006;
        public static final int bottle4=0x7f080002;
        public static final int bottle5=0x7f080003;
        public static final int bottle6=0x7f080004;
        public static final int bottle7=0x7f080007;
        public static final int bottle8=0x7f080008;
        public static final int bottle9=0x7f080009;
        public static final int cancel=0x7f080021;
        public static final int editText1=0x7f08001d;
        public static final int editText2=0x7f08000c;
        public static final int imageView1=0x7f080019;
        public static final int ingredient_label=0x7f08000f;
        public static final int ingredient_list=0x7f080011;
        public static final int ingredient_scroll=0x7f080010;
        public static final int mainButtonVertical=0x7f08001a;
        public static final int pager=0x7f080026;
        public static final int password=0x7f080016;
        public static final int recipe_list=0x7f08000e;
        public static final int recipes_scroll=0x7f08000d;
        public static final int scroll=0x7f080018;
        public static final int scroll1=0x7f08001c;
        public static final int spinner1=0x7f08001e;
        public static final int submit=0x7f080015;
        public static final int switch1=0x7f08001f;
        public static final int tableLayout=0x7f080025;
        public static final int textView1=0x7f080017;
        public static final int textView2=0x7f08001b;
        public static final int textView3=0x7f080020;
        public static final int title=0x7f080027;
        public static final int username=0x7f080014;
    }
    public static final class layout {
        public static final int activity_bottle_layout=0x7f030000;
        public static final int activity_create_drink=0x7f030001;
        public static final int activity_login=0x7f030002;
        public static final int activity_mix_menu=0x7f030003;
        public static final int activity_mix_queue=0x7f030004;
        public static final int activity_settings=0x7f030005;
        public static final int activity_tile_menu=0x7f030006;
        public static final int dark_spinner=0x7f030007;
        public static final int drink_details=0x7f030008;
        public static final int drink_details_tabs=0x7f030009;
        public static final int drink_fragment=0x7f03000a;
        public static final int main_button=0x7f03000b;
    }
    public static final class menu {
        public static final int main=0x7f070000;
    }
    public static final class string {
        public static final int action_mix=0x7f050005;
        public static final int action_queue=0x7f050004;
        public static final int action_settings=0x7f050003;
        public static final int app_name=0x7f050000;
        public static final int cancel=0x7f050006;
        public static final int dummy_button=0x7f050001;
        public static final int dummy_content=0x7f050002;
    }
    public static final class style {
        /** 
        Base application theme, dependent on API level. This theme is replaced
        by AppBaseTheme from res/values-vXX/styles.xml on newer devices.

    

            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.

        

        Base application theme for API 11+. This theme completely replaces
        AppBaseTheme from res/values/styles.xml on API 11+ devices.

    
 API 11 theme customizations can go here. 

        Base application theme for API 14+. This theme completely replaces
        AppBaseTheme from BOTH res/values/styles.xml and
        res/values-v11/styles.xml on API 14+ devices.
    
 API 14 theme customizations can go here. 
         */
        public static final int AppBaseTheme=0x7f060000;
        /**  Application theme. 
 All customizations that are NOT specific to a particular API-level can go here. 
         */
        public static final int AppTheme=0x7f060001;
        public static final int ButtonBar=0x7f060003;
        public static final int ButtonBarButton=0x7f060004;
        public static final int FullscreenActionBarStyle=0x7f060005;
        public static final int FullscreenTheme=0x7f060002;
    }
    public static final class styleable {
        /** 
         Declare custom theme attributes that allow changing which styles are
         used for button bars depending on the API level.
         ?android:attr/buttonBarStyle is new as of API 11 so this is
         necessary to support previous API levels.
    
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarButtonStyle com.uws.radd:buttonBarButtonStyle}</code></td><td></td></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarStyle com.uws.radd:buttonBarStyle}</code></td><td></td></tr>
           </table>
           @see #ButtonBarContainerTheme_buttonBarButtonStyle
           @see #ButtonBarContainerTheme_buttonBarStyle
         */
        public static final int[] ButtonBarContainerTheme = {
            0x7f010000, 0x7f010001
        };
        /**
          <p>This symbol is the offset where the {@link com.uws.radd.R.attr#buttonBarButtonStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name android:buttonBarButtonStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarButtonStyle = 1;
        /**
          <p>This symbol is the offset where the {@link com.uws.radd.R.attr#buttonBarStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name android:buttonBarStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarStyle = 0;
    };
}