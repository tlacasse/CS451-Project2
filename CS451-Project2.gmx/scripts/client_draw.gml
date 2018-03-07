///client_draw()

draw_set_valign(fa_middle);
draw_set_halign(fa_center);

draw_set_color(make_color_rgb(175, 175, 175));
draw_rectangle(0, PLACE_DIV_Y, room_width, room_height, false);
draw_rectangle(PLACE_DIV_X, 0, room_width, room_height, false);

draw_set_color(c_black);
draw_line_width(0, PLACE_DIV_Y, room_width, PLACE_DIV_Y, 3);
draw_line_width(PLACE_DIV_X, 0, PLACE_DIV_X, PLACE_DIV_Y, 3);

if (MAXITEMS > 0){
    if (instance_number(ItemNumbers) == 0){
        instance_create(0,0,ItemNumbers);
    }
    for(var i = 1; i < ITEM_X; i++){
        draw_line(ITEM_WIDTH * i, 0, ITEM_WIDTH * i, PLACE_DIV_Y);
    }
    for(var i = 1; i < ITEM_Y; i++){
        draw_line(0, ITEM_HEIGHT * i, PLACE_DIV_X, ITEM_HEIGHT * i);
    }
    for(var i = 0; i < QUEUE_SIZE-1; i++){
        var xx = room_width - ((i + 1) * QUEUEWIDTH);
        draw_line(xx, PLACE_DIV_Y, xx, room_height);
    }
    for(var i = 0; i < COUNT_CASHIERS-1; i++){
        var yy = (i + 1) * CASHIERHEIGHT;
        draw_line(PLACE_DIV_X, yy, room_width, yy);
    }
    
}
