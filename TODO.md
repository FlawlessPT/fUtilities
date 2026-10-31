# TODO — fUtilities

Pendências conhecidas deste plugin (15/10/2026). Riscar quando feito.

## Funcionalidades
- [ ] Verificar o estado da chuva e adicionar o vidro no menu `/comandos` (nota antiga do `notes.txt`).

- [ ] Mostrar o tempo online de cada jogador na lore da lista `/lista` (`PlayerListMenu`).

## Código
- [ ] `CommandsInventoryClickListener`: reutilizar a mesma lógica dos comandos (dois comentários `TODO` no código).

## Qualidade
- [ ] O fUtilities não tem testes automáticos.

## Migração para Paper 1.21 (24/10/2026)
- `warps.yml` do servidor: o `icone` passa a nome de material do 1.21 e sem `:data` (ex.: `STAINED_GLASS_PANE` já não existe); warps com nome antigo dão aviso de material desconhecido.
- `WarpStoreTest` espera `WOOL` (já não é material válido): falha em runtime, ajustar ou apagar.
- Limpeza menor: `import` de `DyeColor` por usar em `CommandsMenu`, `TagsMenuLayout` com nome completo de `Material`, expressão longa no `ItemInfoCommand`, `WarpTeleporter` com lambda a mais.

- O menu `/comandos` lê o config a cada abertura (o `VariablesManager` foi removido).
