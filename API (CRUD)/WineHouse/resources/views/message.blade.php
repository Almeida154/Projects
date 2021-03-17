<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

{{-- All this code will make appear an alert, it happens cause 
    this code is at views/vendor/page.blade.php (line: 48) --}}

<script>
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 5000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    })
</script>

@if($message = Session::get('success'))
    <script>                    
        Toast.fire({
            icon: 'success',
            title: '{{$message}}'
        })
    </script>
@endif

@if($message = Session::get('error'))
    <script>                    
        Toast.fire({
            icon: 'error',
            title: '{{$message}}'
        })
    </script>
@endif