///update_queue(id, num)

if (argument0 > 0){
    var obj = ds_map_find_value(Client.PEOPLE, argument0);
    if (is_undefined(obj)){
        show_debug_message("Id does not exist: " + string(argument0));
    } else {
        var _gotox = room_width - (QUEUEWIDTH div 2) - (QUEUEWIDTH * argument1);
        var _gotoy = room_height - ((room_height - PLACE_DIV_Y) div 2);
        show_debug_message(string(argument0) + "!!!!" + string(obj.object_index));
        change_goto(obj, _gotox, _gotoy);
    }
}
