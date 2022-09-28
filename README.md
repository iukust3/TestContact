# TestContact
# contact-list-sample
Features implemented using
> # ☛ mvvm </br>
> # ☛ Jetpack Compose UI </br>
> # ☛ Paging 3 </br>
> # ☛ Room Live Data</br>
> # ☛ LazyCoulumn</br>
> # ☛ Navagation Host</br>
> # ☛ Coroutine </br>
# Features: </br>
# 1. Import and parse xml of contacts and save in room database
# 2.Load data using pagging 3 

```
  val contactPagging = Pager(PagingConfig(
        pageSize = 10,
        enablePlaceholders = false,
        maxSize = 30
    )) {
        repository.getAllContacts()
    }.flow.cachedIn(viewModelScope)
```

# 3. Showing all the data in LazyColumn
```
 LazyColumn(
        modifier = modifier.padding(top = 10.dp),

        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(gamesList.itemCount) { index ->
            gamesList[index].let { contact ->
              
                ContactCard(
                    modifier = modifier
                        .padding(8.dp),
                    contact = contact!!,
                    onClickProduct = {

                            onClickToDetailScreen.invoke(contact)

                    }
                )
            }
        }
```
## Screen shots
![screenshot1](https://github.com/iukust3/TestContact/blob/master/main_image.png)

# 4. Delete contact functionality

## Screen shots
![screenshot1](https://github.com/iukust3/TestContact/blob/master/img_delete.png)

# 5. Export Contact as JSON AND XML and share:
 ## [JSON FILE](https://github.com/iukust3/TestContact/blob/master/Contacts.json).</br>
 ## [XML FILE](https://github.com/iukust3/TestContact/blob/master/contats.xml). </br>

 # 6. Edit Contact:

## Screen shots
![screenshot1](https://github.com/iukust3/TestContact/blob/master/img_edit.png)

# 7.Add Contact
## Screen shots
![screenshot1](https://github.com/iukust3/TestContact/blob/master/image_add_new.png)

# 8.View Contact Details

## Screen shots
![screenshot1](https://github.com/iukust3/TestContact/blob/master/img_details.png)
9. Direct Call </br>
10.Direct Email

> Need Search functnolity to implment</br>
> Need WhatsApp and other social links  to implment
