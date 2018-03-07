///update_cashier(id, vid)

var obj = ds_map_find_value(Client.PEOPLE, argument1);

if (is_undefined(obj)){
    show_debug_message("Id does not exist: " + string(argument1));
} else {
    obj.gotox = room_height - ((room_height - PLACE_DIV_X) div 2);
    obj.gotoy = (CASHIERHEIGHT div 2) + (CASHIERHEIGHT * argument0);
        //cashiers are id'ed first, so can just do ^^^
}
