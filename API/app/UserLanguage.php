<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class UserLanguage extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'userId',
        'languageId',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [ ];

    /**
     * Get the user record associated with the userLanguage.
     */
    public function user()
    {
        return $this->belongsTo('App\User', 'userId');
    }

    /**
     * Get the language record associated with the userLanguage.
     */
    public function language()
    {
        return $this->belongsTo('App\Language', 'languageId');
    }
}
