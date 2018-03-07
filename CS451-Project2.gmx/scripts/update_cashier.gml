///update_cashier(id, vid, c_status)

var obj = ds_map_find_value(Client.PEOPLE, argument1);

var cashier_w = (room_width - PLACE_DIV_X) div 3;
var cashier_y = (CASHIERHEIGHT div 2) + (CASHIERHEIGHT * argument0);
//          cashiers are id'ed first, so can just do ^^^

if (is_undefined(obj)){
    if (argument1 > 0){
        show_debug_message("Id does not exist: " + string(argument1));
    }
} else {
    var _gotox = room_width - (cashier_w * 2);
    var _gotoy = cashier_y;
    change_goto(obj, _gotox, _gotoy);
}

obj = ds_map_find_value(Client.PEOPLE, argument0);

if (is_undefined(obj)){
    _Cashier(argument0, argument2, room_width - cashier_w, cashier_y);
} else {
    obj.status = argument2;
}
