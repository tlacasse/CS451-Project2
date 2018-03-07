///visitor_draw()

draw_set_valign(fa_middle);
draw_set_halign(fa_center);

draw_sprite(sprite_visitor,
    ternary(status == VisitorStatus.SHOPPING, 0, 1), x, y);

draw_set_color(c_white);
draw_text(x, y, ID);

draw_set_color(c_black);

draw_text(x, y + 24, string(items) + "/" + string(desired));
