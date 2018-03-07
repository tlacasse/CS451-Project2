///cashier_draw()

draw_set_valign(fa_middle);
draw_set_halign(fa_center);

draw_sprite(sprite_cashier,
    ternary(status == CashierStatus.CHECKINGOUT, 1, 0), x, y);

draw_set_color(c_white);
draw_text(x, y, ID);

draw_set_color(c_black);
