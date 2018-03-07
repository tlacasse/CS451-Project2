///_Cashier(id, status, x, y)

with(instance_create(argument2, argument3, Cashier)){
    ID = argument0;
    status = argument1;
    
    ds_map_add(Client.PEOPLE, argument0, id);
}
