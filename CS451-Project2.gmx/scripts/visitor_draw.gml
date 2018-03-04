///visitor_draw()

draw_set_valign(fa_middle);
draw_set_halign(fa_center);

draw_self();

draw_set_color(c_white);
draw_text(x, y, ID);

draw_set_color(c_black);

draw_text(x, y + 24, string(items) + "/" + string(desired));
