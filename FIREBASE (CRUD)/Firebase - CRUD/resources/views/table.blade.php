@if($drinks != null)
    <div class="container-table-master">
        <div class="container-hyper">
            <div class="table">
            
                <div class="row header">
                    <div class="cell">Bebida</div>
                    <div class="cell">Preço</div>
                    <div class="cell">Marca</div>
                    <div class="cell">Id</div>
                    <div class="cell">Ações</div>
                </div>
        
                @foreach($drinks as $key => $drink)
                    <div class="row">
                        <div class="cell" data-title="Bebida">{{$drink['name']}}</div>
                        <div class="cell" data-title="Preço">R$ {{sprintf("%01.2f", $drink['price'])}}</div>
                        <div class="cell" data-title="Marca">{{$drink['brand']}}</div>
                        <div class="cell" data-title="Id">{{$drink['id']}}</div>
                        <div class="cell" data-title="Ações">
                            <div class="container-buttons">
                                <a key={{$drink['id']}} class="delete" {{--href="{{route('delete', $key)}}"--}}><button type="button" class="btn btn-outline-danger">Excluir</button></a>
                                <a href="{{route('edit', $drink['id'])}}"><button type="button" class="btn btn-outline-success">Editar</button></a>
                            </div>
                        </div>
                    </div>
                @endforeach
        
            </div>
        </div>
    </div>
@else
    <h1 class="flex-c m-t-160 fs-30" style="color: lightgray">Nada registrado :/</h1>
@endif

<script src="https://code.jquery.com/jquery-3.5.1.js"></script>

<script>

    $('.delete').click(function(){
        var key = $(this).attr('key')
        var route = "{{route('delete', ':id')}}"
        route = route.replace(':id', key)
        $.ajax({
            type: 'GET',
            url: route,
            data: route,
            cache: false,
            success: function(data){
                //alert(`Works | ${key}`)
                //console.log(data)
                $('.container-table-master').html(data)
            },
            error: function(){
                alert(`Something went wrong | key => ${key}`)
            }
        })
    })

</script>
