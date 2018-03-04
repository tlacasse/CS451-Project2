///client_draw()

draw_set_valign(fa_top);
draw_set_halign(fa_left);
draw_text(5, 5, ISCONNECTED);

draw_set_valign(fa_middle);
draw_set_halign(fa_center);

if (maxitems > 0){
    for(var i = 0; i < maxitems + 1; i++){
        draw_text(PLACE_START_X * 0.5, PLACE_START_Y + (PLACE_GAP_Y * i), i);
    }
}
