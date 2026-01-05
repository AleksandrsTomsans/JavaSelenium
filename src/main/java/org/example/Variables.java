package org.example;

public final class Variables {

     public static final class xpath {

          public static final String MAIN_PAGE = "https://qatest-dev.indvp.com/";
          public static final String IMAGE_NOT_FOUND = "(/html/body/div[1]/main/div/section/div/div[1]/div[1]/div/div/button/div/span)";

          public static final String TOP_MENU_ELEMENT = "(//li[@class='MenuOverlay-Item'])";    // 8 items in menu with same xpath

          public static final String NEXT_PAGE = "//a[@class='CategoryPagination-Arrow CategoryPagination-Arrow_direction_next']";
          public static final String PRODUCT = "(//p[@itemprop='name' and contains(@class, 'ProductCard-Name') and contains(@class, 'ProductCard-Name_isLoaded')])"; // <p itemprop="name" class="ProductCard-Name ProductCard-Name_isLoaded">...</p>
          public static final String PLD_PRODUCT = "p.ProductActions-Title"; // <p itemprop="name" class="ProductActions-Title">...</p>



     }

     public static final class lists {

          public final int[] PREV_1_2_3_4_5_NEXT = {4, 5, 5, 1, 5, 5, 1, 3};

          public final String[] TOP_MENU_LIST = {
                    "NEW IN",
                    "PORTMEIRION",
                    "KITCHEN & DINNING",
                    "HOME DECOR",
                    "BED & BATH",
                    "GARDEN & OUTDOORS",
                    "GIFTS",
                    "SALES ROOM"
          };
     }
}
