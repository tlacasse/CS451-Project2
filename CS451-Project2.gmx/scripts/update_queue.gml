///update_queue(id, num)

var obj = ds_map_find_value(Client.PEOPLE, argument0);

if (is_undefined(obj)){
    show_debug_message("Id does not exist: " + string(argument0));
} else {
    obj.gotox = room_width - (QUEUEWIDTH div 2) - (QUEUEWIDTH * argument1);
    obj.gotoy = room_height - ((room_height - PLACE_DIV_Y) div 2);
}
