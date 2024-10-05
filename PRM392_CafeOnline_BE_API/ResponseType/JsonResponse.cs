namespace PRM392_CafeOnline_BE_API.ResponseType
{
    public class JsonResponse<T>
    {
        public JsonResponse(T data, int statusCode, string message)
        {
            Value = new Value<T>
            {
                Status = statusCode.ToString(),
                Message = message,
                Data = data
            };
        }

        public Value<T> Value { get; set; }
    }

    public class Value<T>
    {
        public string Status { get; set; }
        public string Message { get; set; }
        public T Data { get; set; }
    }
}
