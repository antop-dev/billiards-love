import aes from '../../src/util/aes256';

test('암복호화 한다.', () => {
  const key = 'L1X02XNZXqkMBlLNvDfheNnPOhDyWEJz4XCSgysX9bc=';
  const text = '드디어 AES256을 했다.';

  const result = aes.encrypt(text, key);
  const decrypted = aes.decrypt(result, key);

  expect(text).toBe(decrypted);
});

test('서버에서 생성한 암호화 값을 복호화한다.', () => {
  const key = 'D7YNCw3GXAyrdbb+AVNT4kggDjY5nX3VsqffNsKnyIM=';
  const text =
    '2pEm0V0ZeFZyOIcgdPhhBUlaxPyHOajzuS7vmQ4OOwNYEsXU0qbaP88+bUo6Tk2tBojEWdExpQ==';

  const decrypted = aes.decrypt(text, key);

  expect(decrypted).toBe('값을 암호화 합니다.');
});
